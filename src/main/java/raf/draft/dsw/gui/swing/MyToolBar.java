package raf.draft.dsw.gui.swing;

import raf.draft.dsw.controller.actions.*;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class MyToolBar extends JToolBar {
    public MyToolBar(ActionMenagerView actionMenagerView) {
        super(HORIZONTAL);
        setBackground(new Color(0xFAFAFA)); // Clean white background
        setFloatable(false);
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(0xE0E0E0)), // Subtle bottom border
                BorderFactory.createEmptyBorder(5, 5, 5, 5) // Padding
        ));

        // Add actions with styled buttons
        addStyledButton(new ExitAction(), "Exit the application", true); // Icon only
        addStyledButton(new AboutUsAction(), "About this application", true); // Icon only
        addStyledButton(new AddProjectAction(), "Create a new project", true); // Icon only
        addStyledButton(new KakoRadi(), "How it works", true); // Icon only
        addStyledButton(new OpenProjectAction(), "Open an existing project", false); // Text only
        addStyledButton(new SaveAsAction(), "Save project as...", false); // Text only
        addStyledButton(new SaveAction(), "Save project", false); // Text only
        addStyledButton(new OpenRoomAction(), "Open a room", false); // Text only
        addStyledButton(new SaveRoomAction(), "Save current room", false); // Text only
        addStyledButton(new OrganizeRoomAction(), "Organize room layout", false); // Text only
    }

    private void addStyledButton(Action action, String tooltip, boolean isIconOnly) {
        JButton button = new JButton(action) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Background for hover/pressed states
                if (getModel().isPressed()) {
                    g2d.setColor(new Color(0xB0B0B0)); // Darker gray when pressed
                } else if (getModel().isRollover()) {
                    g2d.setColor(new Color(0xE8ECEF)); // Light gray on hover
                } else {
                    g2d.setColor(new Color(0xFAFAFA)); // Default background
                }

                // Rounded rectangle
                g2d.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 8, 8));

                super.paintComponent(g2d);
                g2d.dispose();
            }
        };

        button.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        button.setForeground(new Color(0x2D2D2D)); // Dark text
        button.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10)); // Padding
        button.setFocusPainted(false);
        button.setContentAreaFilled(false); // Custom painting
        button.setToolTipText(tooltip); // Add tooltip
        button.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Hand cursor for interactivity

        // Configure icon or text based on isIconOnly
        if (isIconOnly) {
            button.setIcon((Icon) action.getValue(Action.SMALL_ICON)); // Use icon
            button.setText(null); // No text
        } else {
            button.setIcon(null); // No icon
            button.setText((String) action.getValue(Action.NAME)); // Use action name as text
        }

        // Subtle shadow effect
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xE0E0E0), 1),
                BorderFactory.createEmptyBorder(7, 9, 7, 9)
        ));

        add(button);
        add(Box.createHorizontalStrut(6)); // Spacing between buttons
    }
}