package raf.draft.dsw.gui.swing;

import raf.draft.dsw.controller.actions.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class MyMenuBar extends JMenuBar {
    public MyMenuBar() {
        setBackground(new Color(0xF5F6F5));
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Padding

        JMenu fileMenu = new JMenu("File");
        fileMenu.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        fileMenu.setMnemonic(KeyEvent.VK_F);
        // Hover effect for menu
        fileMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                fileMenu.setBackground(new Color(0xE0E0E0));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                fileMenu.setBackground(new Color(0xF5F6F5));
            }
        });

        // Style menu items
        addStyledMenuItem(fileMenu, new ExitAction());
        addStyledMenuItem(fileMenu, new RemoveAction());
        addStyledMenuItem(fileMenu, new RenameAction());
        addStyledMenuItem(fileMenu, new SetAuthorAction());
        addStyledMenuItem(fileMenu, new AddProjectAction());

        add(fileMenu);
    }

    private void addStyledMenuItem(JMenu menu, Action action) {
        JMenuItem menuItem = new JMenuItem(action);
        menuItem.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        menuItem.setBackground(new Color(0xF5F6F5));
        menuItem.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Padding
        menu.add(menuItem);
    }
}