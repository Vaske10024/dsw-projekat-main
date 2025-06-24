package raf.draft.dsw.gui.swing.PopUpWindows;

import javax.swing.*;

public class RotationChoice {
    public static String showOptionsDialog() {

        String[] options = {"Leva rotacija",
                "Desna rotacija"};


        JComboBox<String> choiceBox = new JComboBox<>(options);
        int result = JOptionPane.showConfirmDialog(
                null,
                choiceBox,
                "Izaberite rotaciju elemenata",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );


        if (result == JOptionPane.OK_OPTION) {
            return (String) choiceBox.getSelectedItem();
        } else {
            System.out.println("Nije izabrana nijedna opcija");
            return null;
        }
    }
}
