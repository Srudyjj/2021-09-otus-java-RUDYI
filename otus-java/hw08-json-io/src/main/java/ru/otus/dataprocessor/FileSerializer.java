package ru.otus.dataprocessor;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import ru.otus.model.Measurement;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class FileSerializer implements Serializer {

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final SimpleModule module = new SimpleModule("MeasurementSerializer",
            new Version(1,0,0,null, null,null));
    static {
        module.addSerializer(Measurement.class, new MeasurementSerializer());
        mapper.registerModule(module);
    }

    private final String fileName;

    public FileSerializer(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void serialize(Map<String, Double> data) throws IOException {
        mapper.writeValue(Files.newOutputStream(Paths.get(fileName)), data);
    }
}
