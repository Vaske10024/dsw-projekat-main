package raf.draft.dsw.controller.Serialization;



import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import raf.draft.dsw.model.structures.Elementi.RoomElement;

import java.io.IOException;



import com.fasterxml.jackson.databind.jsontype.TypeSerializer;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import raf.draft.dsw.model.structures.Elementi.RoomElement;

import java.io.IOException;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import raf.draft.dsw.model.structures.Elementi.RoomElement;

import java.io.IOException;

public class RoomElementSerializer extends StdSerializer<RoomElement> {

    public RoomElementSerializer() {
        this(null);
    }

    public RoomElementSerializer(Class<RoomElement> t) {
        super(t);
    }

    @Override
    public void serialize(RoomElement value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        serializeFields(value, gen, provider);
        gen.writeEndObject();
    }

    @Override
    public void serializeWithType(RoomElement value, JsonGenerator gen, SerializerProvider provider, TypeSerializer typeSer) throws IOException {
        typeSer.writeTypePrefixForObject(value, gen);
        serializeFields(value, gen, provider);
        typeSer.writeTypeSuffixForObject(value, gen);
    }

    private void serializeFields(RoomElement value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStringField("@class", value.getClass().getSimpleName().toLowerCase());
        gen.writeStringField("naziv", value.getNaziv());
        gen.writeNumberField("lokacijaX", value.getLokacijaX());
        gen.writeNumberField("lokacijaY", value.getLokacijaY());
        gen.writeNumberField("width", value.getWidth());
        gen.writeNumberField("height", value.getHeight());
        gen.writeNumberField("rotacija", value.getRotacija());
        // Add additional fields as necessary
    }
}


