package raf.draft.dsw.controller.Command;

import raf.draft.dsw.gui.swing.JTree.controller.JTreeActions;
import raf.draft.dsw.gui.swing.RoomElementFactory.RoomElementFactory;
import raf.draft.dsw.gui.swing.RoomView;
import raf.draft.dsw.model.Command;
import raf.draft.dsw.model.structures.Elementi.RoomElement;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.ArrayList;

import java.util.ArrayList;

public class CopyPasteCommand implements Command {
    private final ArrayList<RoomElement> copiedElements;
    private final RoomView roomView;
    private final RoomElementFactory roomElementFactory;
    private final JTreeActions jTreeActions;
    private final int offsetX;
    private final int offsetY;
    private ArrayList<RoomElement> pastedElements;
    private ArrayList<view.painters.Painter> pastedPainters;

    public CopyPasteCommand(ArrayList<RoomElement> copiedElements, RoomView roomView, RoomElementFactory roomElementFactory, JTreeActions jTreeActions, int offsetX, int offsetY) {
        this.copiedElements = new ArrayList<>(copiedElements);
        this.roomView = roomView;
        this.roomElementFactory = roomElementFactory;
        this.jTreeActions = jTreeActions;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.pastedElements = new ArrayList<>();
        this.pastedPainters = new ArrayList<>();
    }

    @Override
    public void execute() {
        pasteCopiedElements();
    }

    @Override
    public void undo() {
        for (RoomElement element : pastedElements) {
            roomView.getRoom().removeChild(element);
            jTreeActions.removeRoomElementFromTree(roomView.getRoom(), element);
        }
        for (view.painters.Painter painter : pastedPainters) {
            roomView.getPainters().remove(painter);
        }
        pastedElements.clear();
        pastedPainters.clear();
        roomView.repaint();
    }

    @Override
    public void redo() {
        pasteCopiedElements();
    }

    private void pasteCopiedElements() {
        if (roomElementFactory == null || copiedElements.isEmpty() || roomView == null) {
            System.out.println("Nothing to paste.");
            return;
        }

        pastedElements.clear();
        pastedPainters.clear();
        boolean overlapDetected = false;
        boolean outOfBoundsDetected = false;

        for (RoomElement copiedElement : copiedElements) {
            if (copiedElement == null) continue;

            RoomElement tempElement = copiedElement.Clone();
            view.painters.Painter painter = roomElementFactory.createPainterForClone(tempElement);

            int attempts = 0;
            while ((isOverlapping(painter) || isOutOfBounds(painter)) && attempts < 100) {
                overlapDetected = isOverlapping(painter);
                outOfBoundsDetected = isOutOfBounds(painter);

                tempElement.setLokacijaX(tempElement.getLokacijaX() + offsetX);
                tempElement.setLokacijaY(tempElement.getLokacijaY() + offsetY);
                painter = roomElementFactory.createPainterForClone(tempElement);

                attempts++;
            }

            if (attempts >= 100) continue;

            roomView.getRoom().addChild(tempElement);
            roomView.addPainter(painter);
            pastedElements.add(tempElement);
            pastedPainters.add(painter);
            jTreeActions.addRoomElementToTree(roomView.getRoom(), tempElement);
        }

        if (pastedElements.isEmpty()) {
            JOptionPane.showMessageDialog(roomView, "No elements could be pasted due to positioning issues.", "Paste Failed", JOptionPane.WARNING_MESSAGE);
        }

        if (overlapDetected) {
            JOptionPane.showMessageDialog(roomView, "Some elements were adjusted to avoid overlapping.", "Overlap Adjusted", JOptionPane.INFORMATION_MESSAGE);
        }
        if (outOfBoundsDetected) {
            JOptionPane.showMessageDialog(roomView, "Some elements were adjusted to stay within bounds.", "Out of Bounds Adjusted", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private boolean isOverlapping(view.painters.Painter painter) {
        Rectangle bounds = painter.getBounds();
        for (view.painters.Painter existingPainter : roomView.getPainters()) {
            if (existingPainter.getBounds().intersects(bounds)) {
                return true;
            }
        }
        return false;
    }

    private boolean isOutOfBounds(view.painters.Painter painter) {
        Rectangle bounds = painter.getBounds();
        int roomWidth = roomView.getWidth();
        int roomHeight = roomView.getHeight();
        return bounds.x < 0 || bounds.y < 0 || bounds.x + bounds.width > roomWidth || bounds.y + bounds.height > roomHeight;
    }
}
