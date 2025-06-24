package raf.draft.dsw.gui.swing.PopUpWindows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RoomElementSize {

    private int width;
    private int height;

    public RoomElementSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }


    public static RoomElementSize promptForSize() {
        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(new JLabel("Width:"));
        JTextField widthField = new JTextField();
        panel.add(widthField);
        panel.add(new JLabel("Height:"));
        JTextField heightField = new JTextField();
        panel.add(heightField);

        int result = JOptionPane.showConfirmDialog(
                null,
                panel,
                "Enter element size",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            try {
                int width = Integer.parseInt(widthField.getText().trim());
                int height = Integer.parseInt(heightField.getText().trim());
                return new RoomElementSize(width, height);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter valid numbers for width and height.", "Error", JOptionPane.ERROR_MESSAGE);
                return null; // Vraćamo null ako su unosi neispravni
            }
        }

        return null; // Vraćamo null ako je korisnik kliknuo Cancel
    }
}