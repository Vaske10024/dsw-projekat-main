package raf.draft.dsw.controller.actions;

import raf.draft.dsw.controller.Serialization.Serializer;
import raf.draft.dsw.gui.swing.MainFrame;
import raf.draft.dsw.model.structures.Project;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

public class SaveAsAction extends AbstractAction {
    private Project project;

    public SaveAsAction() {
        putValue(NAME, "Save As...");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Project project = MainFrame.getUniqueInstance().getProject();
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showSaveDialog(null);
        if (option == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                Serializer.saveProject(project, file);
                JOptionPane.showMessageDialog(null, "Project saved successfully!");
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error saving project: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    private void saveProjectToFile(File file) {
        try {
            // Log before saving
            System.out.println("Saving project to file: " + file.getAbsolutePath());
            Serializer.saveProject(project, file);
            project.setChanged(false); // Reset the changed state
            System.out.println("Project saved successfully.");
        } catch (IOException ex) {
            // Log the error
            System.err.println("Error saving project: " + ex.getMessage());
            JOptionPane.showMessageDialog(null, "Error saving project", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

