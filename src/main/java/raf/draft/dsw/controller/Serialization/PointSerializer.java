package raf.draft.dsw.controller.Serialization;



import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.awt.Point;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;

import java.awt.Point;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;

import java.awt.Point;
import java.io.IOException;

public class PointSerializer extends JsonSerializer<Point> {

    @Override
    public void serialize(Point point, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("x", point.x);
        gen.writeNumberField("y", point.y);
        gen.writeEndObject();
    }

    @Override
    public void serializeWithType(Point point, JsonGenerator gen, SerializerProvider serializers, TypeSerializer typeSer) throws IOException {
        // Započni JSON sa tipom
        typeSer.writeTypePrefixForObject(point, gen);
        // Serijalizuj polja
        gen.writeNumberField("x", point.x);
        gen.writeNumberField("y", point.y);
        // Zatvori JSON sa tipom
        typeSer.writeTypeSuffixForObject(point, gen);
    }
}
