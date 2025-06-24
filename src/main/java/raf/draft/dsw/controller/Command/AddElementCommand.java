package raf.draft.dsw.controller.Command;

import raf.draft.dsw.gui.swing.PopUpWindows.RoomElementSize;
import raf.draft.dsw.gui.swing.RoomElementFactory.RoomElementFactory;
import raf.draft.dsw.gui.swing.RoomView;
import raf.draft.dsw.model.Command;
import raf.draft.dsw.model.structures.Elementi.RoomElement;
import raf.draft.dsw.model.structures.Room;

import java.awt.*;
import java.awt.image.BufferedImage;

import raf.draft.dsw.gui.swing.PopUpWindows.RoomElementSize;
import raf.draft.dsw.gui.swing.RoomElementFactory.RoomElementFactory;
import raf.draft.dsw.gui.swing.RoomView;
import raf.draft.dsw.model.Command;
import raf.draft.dsw.model.structures.Elementi.RoomElement;
import raf.draft.dsw.model.structures.Room;

import java.awt.*;
import java.awt.image.BufferedImage;

import raf.draft.dsw.gui.swing.PopUpWindows.RoomElementSize;
import raf.draft.dsw.gui.swing.RoomElementFactory.RoomElementFactory;
import raf.draft.dsw.gui.swing.RoomView;
import raf.draft.dsw.model.Command;
import raf.draft.dsw.model.structures.Elementi.RoomElement;
import raf.draft.dsw.model.structures.Room;
import raf.draft.dsw.gui.swing.JTree.controller.JTreeActions;
import raf.draft.dsw.gui.swing.MainFrame;

import java.awt.*;
import java.awt.image.BufferedImage;

public class AddElementCommand implements Command {
    private RoomView roomView;
    private Room room;
    private RoomElement element;
    private view.painters.Painter elementPainter;
    private int x, y;
    private RoomElementFactory factory;
    private RoomElementSize size;
    private JTreeActions jTreeActions;

    public AddElementCommand(RoomView roomView, String elementType, int x, int y, RoomElementSize size, RoomElementFactory factory) {
        this.roomView = roomView;
        this.room = roomView.getRoom();
        this.x = x;
        this.y = y;
        this.factory = factory;
        this.size = size;

        // Kreiramo klon elementa sa zadatim tipom
        this.element = factory.createClone(elementType);
        this.jTreeActions = MainFrame.getUniqueInstance().getDraftTreeImplementation().getjTreeActions();
    }

    public RoomElement getElement() {
        return element;
    }

    @Override
    public void execute() {
        try {
            element.setWidth((int) (size.getWidth() * 2.7));
            element.setHeight((int) (size.getHeight() * 2.7));

            Point roomOffset = roomView.drawRoom(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB).getGraphics());
            int centerX = (int) ((x - roomOffset.x) / roomView.getScaleFactor() - element.getWidth() / 2);
            int centerY = (int) ((y - roomOffset.y) / roomView.getScaleFactor() - element.getHeight() / 2);

            int maxX = room.getWidth() - element.getWidth();
            int maxY = room.getHeight() - element.getHeight();
            if (centerX < 0 || centerY < 0 || centerX > maxX || centerY > maxY) {
                System.out.println("Klik je van granica sobe. Element se neće dodati.");
                return;
            }

            element.setLokacijaX(centerX);
            element.setLokacijaY(centerY);

            if (room.doesOverlap(element)) {
                System.out.println("Element se preklapa sa drugim elementom.");
                return;
            }

            if (room.addChild(element)) {
                roomView.switchToRoom(room);
                elementPainter = factory.createPainterForClone(element);
                if (!roomView.getPainters().contains(elementPainter)) {
                    roomView.addPainter(elementPainter);
                    jTreeActions.addRoomElementToTree(room, element);
                    roomView.repaint();
                }
            } else {
                System.out.println("Neuspešno dodavanje elementa.");
            }
        } catch (Exception e) {
            System.out.println("Greška pri dodavanju elementa: " + e.getMessage());
        }
    }

    @Override
    public void undo() {
        if (element != null) {
            room.removeChild(element);
            roomView.getPainters().remove(elementPainter);
            jTreeActions.removeRoomElementFromTree(room,element);  // Dodata funkcionalnost za uklanjanje iz JTree-a
            roomView.repaint();
            System.out.println("Undo: Element je uklonjen.");
        }
    }

    @Override
    public void redo() {
        if (element != null) {
            room.addChild(element);
            roomView.addPainter(elementPainter);
            jTreeActions.addRoomElementToTree(room, element); // Ponovo dodavanje elementa u JTree
            roomView.repaint();
            System.out.println("Redo: Element je ponovo dodat.");
        }
    }
}
