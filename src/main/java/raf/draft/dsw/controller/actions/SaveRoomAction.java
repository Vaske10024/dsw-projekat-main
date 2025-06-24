package raf.draft.dsw.controller.actions;

import raf.draft.dsw.controller.Serialization.Serializer;
import raf.draft.dsw.gui.swing.MainFrame;
import raf.draft.dsw.model.structures.Room;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class SaveRoomAction extends AbstractRoomAction {
    private Room room;

    public SaveRoomAction() {
        putValue(NAME, "Save Room Template");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Dobijamo selektovanu sobu
        room = MainFrame.getUniqueInstance().getJTabbedPaneClass().getSelectedRoom();

        if (room == null) {
            JOptionPane.showMessageDialog(null, "No room selected to save!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Definišemo putanju do foldera za templejte
        File saveDirectory = new File("resources/roomTemplates");
        if (!saveDirectory.exists()) {
            saveDirectory.mkdirs(); // Kreiramo folder ako ne postoji
        }

        // Kreiramo inicijalni fajl sa imenom sobe
        File defaultFile = Paths.get(saveDirectory.getPath(), room.getNaziv() + ".json").toFile();

        // Prikazujemo dijalog za čuvanje fajla
        JFileChooser fileChooser = new JFileChooser(saveDirectory);
        fileChooser.setSelectedFile(defaultFile);

        int option = fileChooser.showSaveDialog(null);
        if (option == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();

            // Proveravamo da li fajl ima ekstenziju .json
            if (!file.getName().toLowerCase().endsWith(".json")) {
                file = new File(file.getAbsolutePath() + ".json");
            }

            try {
                Serializer.saveRoom(room, file);
                JOptionPane.showMessageDialog(null, "Room template saved successfully!");
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error saving room template: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
