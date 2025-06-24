package raf.draft.dsw.controller.Serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import raf.draft.dsw.model.structures.Elementi.Bojler;
import raf.draft.dsw.model.structures.Elementi.RoomElement;
import raf.draft.dsw.model.structures.Room;

import java.io.IOException;




import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import raf.draft.dsw.model.structures.Elementi.Bojler;
import raf.draft.dsw.model.structures.Elementi.RoomElement;

import java.io.IOException;

public class RoomElementDeserializer extends StdDeserializer<RoomElement> {

    public RoomElementDeserializer() {
        super(RoomElement.class);
    }

    @Override
    public RoomElement deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException {
        ObjectMapper mapper = (ObjectMapper) parser.getCodec();
        JsonNode node = mapper.readTree(parser);

        JsonNode typeNode = node.get("@class");

        if (typeNode == null || typeNode.asText().isEmpty()) {
         //   throw new JsonMappingException(parser, "Missing or empty '@class' property in RoomElement.");
            System.out.println("---->"+typeNode);
        }

        String type = typeNode.asText();
        RoomElement element;

        switch (type) {
            case "bojler":
                element = mapper.treeToValue(node, Bojler.class);
                break;
            // Add more cases for other RoomElement types
            default:
                throw new IllegalArgumentException("Unknown type: " + type);
        }

        return element;
    }
}
