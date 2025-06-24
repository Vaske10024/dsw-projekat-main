package raf.draft.dsw.controller.actions;

import com.sun.tools.javac.Main;
import raf.draft.dsw.controller.Serialization.RoomElementDeserializer;
import raf.draft.dsw.controller.Serialization.Serializer;
import raf.draft.dsw.gui.swing.JTree.DraftTreeImplementation;
import raf.draft.dsw.gui.swing.JTree.model.DraftTreeItem;
import raf.draft.dsw.gui.swing.MainFrame;
import raf.draft.dsw.model.nodes.DraftNode;
import raf.draft.dsw.model.nodes.DraftNodeComposite;
import raf.draft.dsw.model.structures.Building;
import raf.draft.dsw.model.structures.Elementi.RoomElement;
import raf.draft.dsw.model.structures.Project;
import raf.draft.dsw.model.structures.Room;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class OpenProjectAction extends AbstractAction {

    public OpenProjectAction() {
        putValue(NAME, "Open Project");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showOpenDialog(null);
        if (option == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                Project project = Serializer.loadProject(file);
                project.setChanged(false);

                // Add project and its children recursively to the tree
                DraftTreeImplementation treeImplementation = MainFrame.getUniqueInstance().getDraftTreeImplementation();
                treeImplementation.getjTreeActions().addProjectToTree(project);



                JOptionPane.showMessageDialog(null, "Project loaded successfully!");
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error loading project: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }



}
