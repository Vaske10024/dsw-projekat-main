package raf.draft.dsw.gui.swing.PopUpWindows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RoomElementOptions {

    public static String showOptionsDialog() {

        String[] options = {
                "bojler", "kada", "krevet", "lavabo", "ormar",
                "sto", "vesMasina", "vrata", "wcSolja"
        };


        JComboBox<String> choiceBox = new JComboBox<>(options);
        int result = JOptionPane.showConfirmDialog(
                null,
                choiceBox,
                "Choose a Room Element",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );


        if (result == JOptionPane.OK_OPTION) {
            return (String) choiceBox.getSelectedItem();
        } else {
            System.out.println("Nije izabran nijedan element");

            return null;
        }
    }
}