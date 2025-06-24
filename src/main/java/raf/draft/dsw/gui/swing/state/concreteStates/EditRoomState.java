package raf.draft.dsw.gui.swing.state.concreteStates;

import raf.draft.dsw.gui.swing.RoomView;
import raf.draft.dsw.gui.swing.state.State;
import raf.draft.dsw.model.structures.Room;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class EditRoomState implements State {

    @Override
    public void naKlikMisa(RoomView roomView, int x, int y) {
        if(roomView.getPainters().isEmpty()){
            setSize(roomView.getRoom());
            roomView.fitToPanel();
        }
        else{
            System.out.println("Ne mozete da menjate velicinu sobe koja ima elemente");
        }
        roomView.repaint();
    }

    @Override
    public void naMouseDragged(RoomView roomView, int x, int y) {

    }

    @Override
    public void naPretisnutoDugme(RoomView roomView, String key) {

    }

    @Override
    public void naZoom(RoomView roomView, double zoomFactor) {

    }

    public boolean setSize(Room room) {
        JTextField widthField = new JTextField(5);
        JTextField heightField = new JTextField(5);

        // Dodavanje filtera za unos samo brojeva
        addNumberOnlyFilter(widthField);
        addNumberOnlyFilter(heightField);

        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("Width:"));
        myPanel.add(widthField);
        myPanel.add(Box.createHorizontalStrut(15)); // Prazan prostor između polja
        myPanel.add(new JLabel("Height:"));
        myPanel.add(heightField);

        while (true) {
            int result = JOptionPane.showConfirmDialog(
                    null,
                    myPanel,
                    "Set Room Dimensions",
                    JOptionPane.OK_CANCEL_OPTION
            );

            if (result == JOptionPane.CANCEL_OPTION || result == JOptionPane.CLOSED_OPTION) {
                // Ako korisnik klikne "Cancel" ili zatvori prozor, prekidamo funkciju
                return false;
            }

            try {
                int width = Integer.parseInt(widthField.getText());
                int height = Integer.parseInt(heightField.getText());

                if (width <= 0 || height <= 0) {
                    throw new NumberFormatException(); // Negativne ili nulte vrednosti nisu dozvoljene
                }

                // Postavljanje dimenzija prostorije
                room.setWidth(width);
                room.setHeight(height);
                return true; // Uspešan unos
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(
                        null,
                        "Vrednosti za visinu i sirinu sobe moraju biti pozitivne!",
                        "Greska unosa",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }


    }
    private void addNumberOnlyFilter(JTextField textField) {
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                // Dozvoljavamo samo cifre i brisanje (backspace)
                if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE) {
                    e.consume(); // Sprecava unos nevaljanih karaktera
                }
            }
        });
    }
}
