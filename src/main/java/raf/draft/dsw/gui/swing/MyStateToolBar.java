package raf.draft.dsw.gui.swing;

import raf.draft.dsw.controller.actions.*;
import raf.draft.dsw.controller.actions.stateActions.*;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class MyStateToolBar extends JToolBar {
    public MyStateToolBar(ActionMenagerState actionMenagerState) {
        super(VERTICAL);
        setBackground(new Color(0xFAFAFA)); // Clean white background
        setFloatable(false);
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 1, 0, 0, new Color(0xE0E0E0)), // Subtle left border
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        addStyledButton(actionMenagerState.addStateAction, "Add new element");
        addStyledButton(actionMenagerState.selectStateAction, "Select elements");
        addStyledButton(actionMenagerState.deleteStateAction, "Delete selected elements");
        addStyledButton(actionMenagerState.editStateAction, "Edit element properties");
        addStyledButton(actionMenagerState.moveStateAction, "Move elements");
        addStyledButton(actionMenagerState.resizeStateAction, "Resize elements");
        addStyledButton(actionMenagerState.rotateStateAction, "Rotate elements");
        addStyledButton(actionMenagerState.copyPaseStateAction, "Copy and paste elements");
        addStyledButton(actionMenagerState.zoomStateAction, "Zoom in/out");
    }

    private void addStyledButton(Action action, String tooltip) {
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
        button.setForeground(new Color(0x2D2D2D));
        button.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setToolTipText(tooltip);
        button.setIcon((Icon) action.getValue(Action.SMALL_ICON)); // Ensure action has an icon
        button.setText(null); // Icon-only for consistency
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Subtle shadow effect
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xE0E0E0), 1),
                BorderFactory.createEmptyBorder(7, 9, 7, 9)
        ));

        add(button);
        add(Box.createVerticalStrut(6));
    }
}