package raf.draft.dsw.controller.Serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.awt.Color;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.awt.Color;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.awt.Color;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.awt.Color;
import java.io.IOException;

public class ColorDeserializer extends JsonDeserializer<Color> {

    @Override
    public Color deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String colorString = p.getText();

        try {
            if (colorString.length() == 9 && colorString.startsWith("#")) {
                // Format: #AARRGGBB
                int alpha = Integer.parseInt(colorString.substring(1, 3), 16);
                int red = Integer.parseInt(colorString.substring(3, 5), 16);
                int green = Integer.parseInt(colorString.substring(5, 7), 16);
                int blue = Integer.parseInt(colorString.substring(7, 9), 16);
                return new Color(red, green, blue, alpha);
            } else if (colorString.length() == 7 && colorString.startsWith("#")) {
                // Format: #RRGGBB
                return Color.decode(colorString);
            } else {
                throw new IllegalArgumentException("Invalid color format: " + colorString);
            }
        } catch (NumberFormatException e) {
            throw new IOException("Failed to parse color: " + colorString, e);
        }
    }
}

