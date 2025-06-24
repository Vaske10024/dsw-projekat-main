package raf.draft.dsw.gui.swing.PopUpWindows;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class RoomElementOptions {
    public static String showOptionsDialog() {
        // Set global UIManager properties for JOptionPane
        UIManager.put("OptionPane.background", new Color(0xFAFAFA));
        UIManager.put("Panel.background", new Color(0xFAFAFA));
        UIManager.put("OptionPane.messageFont", new Font("Segoe UI", Font.PLAIN, 14));
        UIManager.put("OptionPane.buttonFont", new Font("Segoe UI", Font.PLAIN, 13));
        UIManager.put("Button.background", new Color(0xFAFAFA));
        UIManager.put("Button.foreground", new Color(0x2D2D2D));
        UIManager.put("Button.border", BorderFactory.createEmptyBorder(8, 12, 8, 12));

        // Create styled panel
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(0xFAFAFA));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Style label
        JLabel label = new JLabel("Select a room element:");
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        label.setForeground(new Color(0x2D2D2D));
        panel.add(label, BorderLayout.NORTH);

        // Style combo box
        String[] options = {"bojler", "kada", "krevet", "lavabo", "ormar", "sto", "vesMasina", "vrata", "wcSolja"};
        JComboBox<String> choiceBox = new JComboBox<>(options);
        choiceBox.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        choiceBox.setBackground(new Color(0xFFFFFF));
        choiceBox.setForeground(new Color(0x2D2D2D));
        choiceBox.setBorder(BorderFactory.createLineBorder(new Color(0xE0E0E0), 1));
        panel.add(choiceBox, BorderLayout.CENTER);

        // Show dialog with styled buttons
        JOptionPane optionPane = new JOptionPane(panel, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
        JDialog dialog = optionPane.createDialog(null, "Choose a Room Element");
        dialog.getContentPane().setBackground(new Color(0xFAFAFA));

        // Style buttons
        for (Component comp : optionPane.getComponents()) {
            if (comp instanceof JPanel buttonPanel) {
                for (Component button : ((JPanel) buttonPanel).getComponents()) {
                    if (button instanceof JButton) {
                        styleButton((JButton) button);
                    }
                }
            }
        }

        dialog.setVisible(true);

        int result = (Integer) optionPane.getValue();
        if (result == JOptionPane.OK_OPTION) {
            return (String) choiceBox.getSelectedItem();
        } else {
            System.out.println("Nije izabran nijedan element");
            return null;
        }
    }

    private static void styleButton(JButton button) {
        button.setOpaque(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setForeground(new Color(0x2D2D2D));
        button.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0xE8ECEF));
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(null);
            }
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0xB0B0B0));
            }
            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                button.setBackground(button.getModel().isRollover() ? new Color(0xE8ECEF) : null);
            }
        });

        button.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override
            protected void paintButtonPressed(Graphics g, AbstractButton b) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(0xB0B0B0));
                g2d.fill(new RoundRectangle2D.Float(0, 0, b.getWidth(), b.getHeight(), 8, 8));
                g2d.dispose();
            }


            protected void paintBackground(Graphics g, AbstractButton b) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (b.getModel().isRollover()) {
                    g2d.setColor(new Color(0xE8ECEF));
                } else {
                    g2d.setColor(new Color(0xFAFAFA));
                }
                g2d.fill(new RoundRectangle2D.Float(0, 0, b.getWidth(), b.getHeight(), 8, 8));
                g2d.dispose();
            }
        });
    }
}