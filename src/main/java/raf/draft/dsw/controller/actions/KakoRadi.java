package raf.draft.dsw.controller.actions;

import raf.draft.dsw.gui.swing.JTree.controller.JTreeActions;
import raf.draft.dsw.gui.swing.JTree.model.DraftTreeItem;
import raf.draft.dsw.gui.swing.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.net.URL;

public class KakoRadi extends AbstractRoomAction{
    public KakoRadi() {
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        putValue(SMALL_ICON, loadIcon("/images/easterEgg.png"));
        putValue(NAME, "Kako Radi");
        putValue(SHORT_DESCRIPTION, "Kako Radi");}

    @Override
    public void actionPerformed(ActionEvent e) {
        // Create a new JFrame for displaying the GIF
        JFrame gifFrame = new JFrame("Kako Radi");
        gifFrame.setSize(500, 500);
        gifFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Load the GIF from the resources folder
        URL gifURL = getClass().getClassLoader().getResource("images/khabib-not-me.gif");
        if (gifURL != null) {
            Icon gifIcon = new ImageIcon(gifURL);
            JLabel gifLabel = new JLabel(gifIcon);
            gifFrame.add(gifLabel);
        } else {
            JOptionPane.showMessageDialog(null, "GIF not found!", "Error", JOptionPane.ERROR_MESSAGE);
        }

        gifFrame.setLocationRelativeTo(null);
        // Set the frame to be visible
        gifFrame.setVisible(true);
    }


}
