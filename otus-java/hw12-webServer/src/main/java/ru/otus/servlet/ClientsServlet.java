package ru.otus.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.otus.crm.model.Address;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;
import ru.otus.crm.service.DBServiceClient;
import ru.otus.service.ClientTemplate;
import ru.otus.service.TemplateProcessor;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ClientsServlet extends HttpServlet {

    private static final String CLIENTS_PAGE_TEMPLATE = "clients.html";
    private static final String TEMPLATE_ATTR_CLIENTS = "clients";

    private static final String CLIENT_NAME = "name";
    private final String CLIENT_ADDRESS= "address";
    private final String CLIENT_PHONES = "phones";

    private final TemplateProcessor templateProcessor;
    private final DBServiceClient serviceClient;

    public ClientsServlet(TemplateProcessor templateProcessor, DBServiceClient serviceClient) {
        this.templateProcessor = templateProcessor;
        this.serviceClient = serviceClient;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<String, Object> paramsMap = new HashMap<>();
        List<ClientTemplate> clients = serviceClient
                .findAll()
                .stream()
                .map(ClientTemplate::new)
                .collect(Collectors.toList());
        paramsMap.put(TEMPLATE_ATTR_CLIENTS, clients);

        resp.setContentType("text/html");
        resp.getWriter().println(templateProcessor.getPage(CLIENTS_PAGE_TEMPLATE, paramsMap));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var name = req.getParameter(CLIENT_NAME);
        var address = new Address(req.getParameter(CLIENT_ADDRESS));
        var phones = List.of(req.getParameter(CLIENT_PHONES).split(";"))
                .stream()
                .map(Phone::new)
                .toList();

        serviceClient.saveClient(new Client(name, address, phones));
        resp.sendRedirect("clients");
    }
}
