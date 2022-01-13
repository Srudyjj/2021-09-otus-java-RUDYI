package ru.otus.dataprocessor;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import ru.otus.model.Measurement;

import java.io.IOException;
import java.util.List;

public class ResourcesFileLoader implements Loader {

    private final ObjectMapper mapper = new ObjectMapper();
    private final String fileName;

    public ResourcesFileLoader(String fileName) {
        this.fileName = fileName;
        SimpleModule module =
                new SimpleModule("MeasurementDeserializer", new Version(1,0,0,null, null,null));
        module.addDeserializer(Measurement.class, new MeasurementDeserializer());
        mapper.registerModule(module);
    }

    @Override
    public List<Measurement> load() throws IOException {
        var resource = ResourcesFileLoader.class.getClassLoader().getResource(fileName);
        return mapper.readValue(resource, new TypeReference<List<Measurement>>() {
        });
    }
}
