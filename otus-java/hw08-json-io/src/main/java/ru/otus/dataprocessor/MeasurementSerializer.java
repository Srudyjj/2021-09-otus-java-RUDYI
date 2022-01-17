package ru.otus.dataprocessor;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import ru.otus.model.Measurement;

import java.io.IOException;

public final class MeasurementSerializer extends StdSerializer<Measurement> {

    public MeasurementSerializer() {
        this(null);
    }

    private MeasurementSerializer(Class<Measurement> t) {
        super(t);
    }

    @Override
    public void serialize(Measurement value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeObject(value);
        gen.writeEndObject();
    }
}
