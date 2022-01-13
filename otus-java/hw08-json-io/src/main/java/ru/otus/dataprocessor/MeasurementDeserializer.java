package ru.otus.dataprocessor;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import ru.otus.model.Measurement;

import java.io.IOException;

public class MeasurementDeserializer extends StdDeserializer<Measurement> {

    public MeasurementDeserializer() {
        this(null);
    }

    protected MeasurementDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Measurement deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        var codec = p.getCodec();
        JsonNode node = codec.readTree(p);
        JsonNode nameNode = node.get("name");
        JsonNode valueNode = node.get("value");

        return new Measurement(nameNode.asText(), valueNode.doubleValue());
    }
}
