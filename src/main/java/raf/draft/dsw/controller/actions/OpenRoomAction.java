package raf.draft.dsw.controller.actions;

import com.sun.tools.javac.Main;
import raf.draft.dsw.controller.Serialization.Serializer;
import raf.draft.dsw.gui.swing.MainFrame;
import raf.draft.dsw.model.nodes.DraftNode;
import raf.draft.dsw.model.structures.Elementi.RoomElement;
import raf.draft.dsw.model.structures.Room;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

public class OpenRoomAction extends AbstractRoomAction {
    private Room room;

    public OpenRoomAction() {
        putValue(NAME, "Load Room Template");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.room=new Room();

        JFileChooser fileChooser = new JFileChooser("resources/roomTemplates");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("JSON Files", "json"));

        int option = fileChooser.showOpenDialog(null);
        if (option == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                Room loadedRoom = Serializer.loadRoom(file);
                room.copyFrom(loadedRoom);
                JOptionPane.showMessageDialog(null, "Room template loaded successfully!");
                MainFrame.getUniqueInstance().getDraftTreeImplementation().getjTreeActions().addNodeToTree(room,MainFrame.getUniqueInstance().getDraftTreeImplementation().getCurrentlySelectedTreeItem().getDraftNode());
                for(DraftNode element:room.getChildren()){
                    RoomElement roomElement = (RoomElement) element;
                    MainFrame.getUniqueInstance().getDraftTreeImplementation().getjTreeActions().addRoomElementToTree(room,roomElement);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error loading room template: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
