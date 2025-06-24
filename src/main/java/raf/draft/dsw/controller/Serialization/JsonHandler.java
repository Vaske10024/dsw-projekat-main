package raf.draft.dsw.controller.Serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ser.std.StringSerializer;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import com.fasterxml.jackson.databind.module.SimpleModule;

import java.awt.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.jsontype.DefaultBaseTypeLimitingValidator;
import java.awt.Color;

import com.fasterxml.jackson.databind.module.SimpleModule;
import raf.draft.dsw.model.structures.Elementi.RoomElement;

public class JsonHandler {
    public static ObjectMapper getMapper() {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(Color.class, new ColorSerializer());
        module.addDeserializer(Color.class, new ColorDeserializer());
        module.addSerializer(Point.class, new PointSerializer());
        module.addDeserializer(Point.class, new PointDeserializer());
       // module.addSerializer(RoomElement.class, new RoomElementSerializer());
        //module.addDeserializer(RoomElement.class, new RoomElementDeserializer());
        mapper.registerModule(module);
        mapper.activateDefaultTyping(
                new DefaultBaseTypeLimitingValidator(),
                ObjectMapper.DefaultTyping.NON_FINAL
        );
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.enable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        return mapper;
    }

}
