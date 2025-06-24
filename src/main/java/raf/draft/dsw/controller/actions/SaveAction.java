package raf.draft.dsw.controller.actions;

import raf.draft.dsw.controller.Serialization.Serializer;
import raf.draft.dsw.gui.swing.MainFrame;
import raf.draft.dsw.model.structures.Project;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

public class SaveAction extends AbstractAction {
    private Project project;

    public SaveAction() {
        putValue(NAME, "Save");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        project = MainFrame.getUniqueInstance().getProject();

        if (project.getPutanjaDoProjekta() == null) {
            JFileChooser fileChooser = new JFileChooser();
            int option = fileChooser.showSaveDialog(null);
            if (option == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                project.setPutanjaDoProjekta(file.getAbsolutePath());
                saveProjectToFile(file);
            }
        } else {
            saveProjectToFile(new File(project.getPutanjaDoProjekta()));
        }
    }

    private void saveProjectToFile(File file) {
        try {
            Serializer.saveProject(project, file);
            project.setChanged(false); // Resetujemo stanje promena
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error saving project", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
