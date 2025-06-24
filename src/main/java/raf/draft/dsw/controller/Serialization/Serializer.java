package raf.draft.dsw.controller.Serialization;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import raf.draft.dsw.model.structures.Project;
import raf.draft.dsw.model.structures.Room;

import java.io.File;
import java.io.IOException;

public class Serializer {
    private static final ObjectMapper objectMapper = JsonHandler.getMapper();

    static {
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    public static void saveProject(Project project, File file) throws IOException {
        objectMapper.writeValue(file, project);
    }

    public static Project loadProject(File file) throws IOException {
        return objectMapper.readValue(file, Project.class);
    }
    public static void saveRoom(Room room, File file) throws IOException {
        objectMapper.writeValue(file, room);
    }

    // Metoda za učitavanje sobe
    public static Room loadRoom(File file) throws IOException {
        try {
            return objectMapper.readValue(file, Room.class);
        } catch (IOException e) {
            throw new IOException("Failed to parse room JSON: " + e.getMessage(), e);
        }
    }
}
