package raf.draft.dsw.gui.swing.JTree;

import raf.draft.dsw.controller.messagegenerator.MessageGenerator;
import raf.draft.dsw.gui.swing.MainFrame;
import raf.draft.dsw.gui.swing.Painter.BojlerPainter;
import raf.draft.dsw.model.messages.MessageType;
import raf.draft.dsw.model.nodes.DraftNode;
import raf.draft.dsw.model.structures.Building;
import raf.draft.dsw.model.structures.Elementi.Bojler;
import raf.draft.dsw.model.structures.Project;
import raf.draft.dsw.model.structures.ProjectExplorer;
import raf.draft.dsw.model.structures.Room;

import javax.swing.*;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

import static javax.swing.JOptionPane.showOptionDialog;

public class ChildFactory {

    public static int counter=0;
    private static Random random = new Random();



    public DraftNode createChild(DraftNode parent, MessageGenerator messageGenerator){
        if(parent instanceof ProjectExplorer){
            counter++;
            Project project = new Project(String.format("Project %d", counter),parent);
            MainFrame.getUniqueInstance().setProject(project);
            return project;

        }
        if(parent instanceof Project){
            DraftNode node= showOptionDialog(new JFrame(),"Izaberi sta hoces da napravis",parent);
            if(node==null){

            }
            else{
                return node;
            }
        }
        if(parent instanceof Building){
            int randomNumber = random.nextInt(1000);
            String roomName = "Soba " + randomNumber;
            Room room = new Room(roomName, parent);
            boolean bool=setSize(room);

            if(bool){
                room.setColor(((Building) parent).getColor());
                return room;
            }
        }

        return null;
    }


    private DraftNode showOptionDialog(JFrame parent, String message, DraftNode tata) {

        Object[] options = {"Building", "Room"};

        int choice = JOptionPane.showOptionDialog(parent,
                message,
                "Izaberite hocete li sobu ili zgradu",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);


        if (choice == JOptionPane.YES_OPTION) {
            int randomNumber123 = random.nextInt(1000);
            String buildingName = "Building" + randomNumber123;
            return new Building(buildingName,tata);
        } else if (choice == JOptionPane.NO_OPTION) {


            int randomNumber = random.nextInt(1000);
            String roomName = "Soba " + randomNumber;
            Room room = new Room(roomName, tata);
            boolean bool=setSize(room);

            if(bool){
                room.setColor(Color.GRAY);
                return room;
            }

        } else {
            return null;
        }
        return null;
    }

    // Upgradovana metoda setSize i pomocna metoda sa listenerima za unos samo brojeva
    // Koristimo try i catch
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

    // Pomocna metoda za dodavanje filtera za unos samo brojeva
    // Koristi listener koji osluskuje unos sa testature da bi se osigurano uneli samo brojevi
    // Tj. ignorise karaktere koji nisu brojevi
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
