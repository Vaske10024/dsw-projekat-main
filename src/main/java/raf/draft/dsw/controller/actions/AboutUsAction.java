package raf.draft.dsw.controller.actions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class AboutUsAction extends AbstractRoomAction {

    @Override
    public void actionPerformed(ActionEvent e) {
        String message = "Studenti koji rade na projektu:\n" +
                "1.Vasilije \n" +
                "2. Jovan   \n";
        showCustomDialog(message, "src/main/resources/images/vasa.jpg", "src/main/resources/images/kolega.png");
    }

    public AboutUsAction() {
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F5, ActionEvent.ALT_MASK));
        putValue(SMALL_ICON, loadIcon("/images/aboutUs.png"));
        putValue(NAME, "About Us");
        putValue(SHORT_DESCRIPTION, "About Us");
    }

    private void showCustomDialog(String message, String iconPath1, String iconPath2) {

        JDialog dialog = new JDialog((JFrame) null, "About Us", true);
        dialog.setLayout(new FlowLayout());
        dialog.setSize(300, 150);


        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));


        String[] lines = message.split("\n");
        for (int i = 0; i < lines.length; i++) {

            JLabel label = new JLabel(lines[i]);


            if (i == 1) {
                label.setIcon(resizeImageIcon(iconPath1, 50, 50));
            } else if (i == 2) {
                label.setIcon(resizeImageIcon(iconPath2, 50, 50));
            }


            label.setHorizontalTextPosition(JLabel.RIGHT);
            label.setVerticalTextPosition(JLabel.CENTER);


            panel.add(label);
        }


        dialog.add(panel);


        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> dialog.dispose());
        dialog.add(okButton);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    private ImageIcon resizeImageIcon(String iconPath, int width, int height) {
        ImageIcon originalIcon = new ImageIcon(iconPath);
        Image resizedImage = originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }

}
