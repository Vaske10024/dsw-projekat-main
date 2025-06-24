package raf.draft.dsw.gui.swing.PopUpWindows;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

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
        // Set UIManager properties
        UIManager.put("OptionPane.background", new Color(0xFAFAFA));
        UIManager.put("Panel.background", new Color(0xFAFAFA));
        UIManager.put("OptionPane.messageFont", new Font("Segoe UI", Font.PLAIN, 14));
        UIManager.put("OptionPane.buttonFont", new Font("Segoe UI", Font.PLAIN, 13));
        UIManager.put("Button.background", new Color(0xFAFAFA));
        UIManager.put("Button.foreground", new Color(0x2D2D2D));
        UIManager.put("Button.border", BorderFactory.createEmptyBorder(8, 12, 8, 12));

        // Create styled panel
        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        panel.setBackground(new Color(0xFAFAFA));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Style labels
        JLabel widthLabel = new JLabel("Width:");
        widthLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        widthLabel.setForeground(new Color(0x2D2D2D));
        panel.add(widthLabel);

        // Style text fields
        JTextField widthField = new JTextField(10);
        widthField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        widthField.setBackground(new Color(0xFFFFFF));
        widthField.setForeground(new Color(0x2D2D2D));
        widthField.setBorder(BorderFactory.createLineBorder(new Color(0xE0E0E0), 1));
        panel.add(widthField);

        JLabel heightLabel = new JLabel("Height:");
        heightLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        heightLabel.setForeground(new Color(0x2D2D2D));
        panel.add(heightLabel);

        JTextField heightField = new JTextField(10);
        heightField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        heightField.setBackground(new Color(0xFFFFFF));
        heightField.setForeground(new Color(0x2D2D2D));
        heightField.setBorder(BorderFactory.createLineBorder(new Color(0xE0E0E0), 1));
        panel.add(heightField);

        // Show dialog with styled buttons
        JOptionPane optionPane = new JOptionPane(panel, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
        JDialog dialog = optionPane.createDialog(null, "Enter Element Size");
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
            try {
                int width = Integer.parseInt(widthField.getText().trim());
                int height = Integer.parseInt(heightField.getText().trim());
                return new RoomElementSize(width, height);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter valid numbers for width and height.", "Error", JOptionPane.ERROR_MESSAGE);
                return null;
            }
        }

        return null;
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