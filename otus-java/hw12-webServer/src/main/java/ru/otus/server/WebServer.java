package ru.otus.server;

import com.google.gson.Gson;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.otus.crm.service.DBServiceClient;
import ru.otus.helpers.FileSystemHelper;
import ru.otus.service.UserAuthService;
import ru.otus.service.TemplateProcessor;
import ru.otus.servlet.AuthorizationFilter;
import ru.otus.servlet.ClientApiServlet;
import ru.otus.servlet.ClientsServlet;
import ru.otus.servlet.LoginServlet;

import java.util.Arrays;

public class WebServer {
    private static final String START_PAGE_NAME = "index.html";
    private static final String COMMON_RESOURCES_DIR = "static";

    private final DBServiceClient serviceClient;
    private final UserAuthService authService;
    private final Gson gson;
    private final TemplateProcessor templateProcessor;
    private final Server server;

    public WebServer(int port, DBServiceClient serviceClient, UserAuthService authService, Gson gson, TemplateProcessor templateProcessor) {
        this.serviceClient = serviceClient;
        this.authService = authService;
        this.gson = gson;
        this.templateProcessor = templateProcessor;
        this.server = new Server(port);
    }

    public void start() throws Exception {
        if (server.getHandlers().length == 0) {
            initContext();
        }
        server.start();
    }

    public void join() throws Exception {
        server.join();
    }

    public void stop() throws Exception {
        server.stop();
    }

    private Server initContext() {

        ResourceHandler resourceHandler = createResourceHandler();
        ServletContextHandler servletContextHandler = createServletContextHandler();

        HandlerList handlers = new HandlerList();
        handlers.addHandler(resourceHandler);
        handlers.addHandler(applySecurity(servletContextHandler, "/clients", "/api/client/*"));


        server.setHandler(handlers);
        return server;
    }

    private Handler applySecurity(ServletContextHandler servletContextHandler, String ...paths) {
        AuthorizationFilter authorizationFilter = new AuthorizationFilter();
        Arrays.stream(paths)
                .forEachOrdered(path -> servletContextHandler.addFilter(new FilterHolder(authorizationFilter), path, null));
        return servletContextHandler;
    }

    private ResourceHandler createResourceHandler() {
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(false);
        resourceHandler.setWelcomeFiles(new String[]{START_PAGE_NAME});
        resourceHandler.setResourceBase(FileSystemHelper.localFileNameOrResourceNameToFullPath(COMMON_RESOURCES_DIR));
        return resourceHandler;
    }

    private ServletContextHandler createServletContextHandler() {
        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        servletContextHandler.addServlet(new ServletHolder(new LoginServlet(templateProcessor, authService)), "/login");
        servletContextHandler.addServlet(new ServletHolder(new ClientsServlet(templateProcessor, serviceClient)), "/clients");
        servletContextHandler.addServlet(new ServletHolder(new ClientApiServlet(serviceClient, gson)), "/api/client/*");
        return servletContextHandler;
    }
}
