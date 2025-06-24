package raf.draft.dsw.gui.swing.state.concreteStates;

import raf.draft.dsw.controller.Command.RotateCommand;
import raf.draft.dsw.controller.actions.stateActions.RotateStateAction;
import raf.draft.dsw.gui.swing.MainFrame;
import raf.draft.dsw.gui.swing.RoomView;
import raf.draft.dsw.gui.swing.state.State;
import raf.draft.dsw.model.structures.Elementi.RoomElement;
import view.painters.Painter;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Map;

public class RotateState implements State {

    private Map<Painter, Integer> initialRotations = new HashMap<>(); // Initial rotations for all selected painters

    @Override
    public void naKlikMisa(RoomView roomView, int x, int y) {
        String tipRotacije = roomView.getMediator().getTipRotacije();
        int rotationDelta = 0;
        if ("desno".equals(tipRotacije)) {
            rotationDelta = 90;
        } else if ("levo".equals(tipRotacije)) {
            rotationDelta = -90;
        }

        // Zabeleži početne rotacije
        Map<Painter, Integer> initialRotations = new HashMap<>();
        for (Painter painter : roomView.getMediator().getSelectedNodes()) {
            RoomElement element = painter.getRoomElement();
            if (element != null) {
                initialRotations.put(painter, element.getRotacija());
            }
        }

        // Izvrši rotaciju
        rotateElements(roomView, rotationDelta);

        // Zabeleži nove rotacije
        Map<Painter, Integer> newRotations = new HashMap<>();
        for (Painter painter : roomView.getMediator().getSelectedNodes()) {
            RoomElement element = painter.getRoomElement();
            if (element != null) {
                newRotations.put(painter, element.getRotacija());
            }
        }

        // Kreiraj i izvrši RotateCommand
        RotateCommand rotateCommand = new RotateCommand(roomView, initialRotations, newRotations);
        MainFrame.getUniqueInstance().getCommandHistory().executeCommand(rotateCommand);
        roomView.getCommandHistory().executeCommand(rotateCommand);
    }



    private void rotateElements(RoomView roomView, int rotationDelta) {
        for (Painter painter : roomView.getMediator().getSelectedNodes()) {
            RoomElement element = painter.getRoomElement();
            if (element != null) {
                int novaRotacija = (element.getRotacija() + rotationDelta) % 360;

                Rectangle rotatedBounds = getRotatedBounds(element, novaRotacija);

                if (isWithinRoomBounds(rotatedBounds, roomView) && !isOverlappingWithOtherElements(rotatedBounds, roomView, element)) {
                    element.setRotacija(novaRotacija);
                } else {
                    JOptionPane.showMessageDialog(roomView, "Rotation is not possible due to overlap or out of bounds", "Warning", JOptionPane.WARNING_MESSAGE);
                }
            }
        }

        roomView.repaint();
    }


    private Rectangle getRotatedBounds(RoomElement element, int rotation) {
        Rectangle bounds = new Rectangle(element.getLokacijaX(), element.getLokacijaY(), element.getWidth(), element.getHeight());

        AffineTransform transform = new AffineTransform();
        int centerX = element.getLokacijaX() + element.getWidth() / 2;
        int centerY = element.getLokacijaY() + element.getHeight() / 2;
        transform.rotate(Math.toRadians(rotation), centerX, centerY);

        Shape transformedBounds = transform.createTransformedShape(bounds);
        Rectangle2D rotatedBounds = transformedBounds.getBounds2D();

        return new Rectangle(
                (int) rotatedBounds.getX(),
                (int) rotatedBounds.getY(),
                (int) rotatedBounds.getWidth(),
                (int) rotatedBounds.getHeight()
        );
    }

    private boolean isWithinRoomBounds(Rectangle bounds, RoomView roomView) {
        int roomWidth = roomView.getRoom().getWidth();
        int roomHeight = roomView.getRoom().getHeight();

        return bounds.x >= 0 && bounds.y >= 0 &&
                bounds.x + bounds.width <= roomWidth &&
                bounds.y + bounds.height <= roomHeight;
    }

    private boolean isOverlappingWithOtherElements(Rectangle bounds, RoomView roomView, RoomElement element) {
        for (Painter otherPainter : roomView.getPainters()) {
            RoomElement otherElement = otherPainter.getRoomElement();
            if (otherElement != element) {
                Rectangle otherBounds = otherPainter.getBounds();
                if (bounds.intersects(otherBounds)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void naMouseDragged(RoomView roomView, int x, int y) {
        // Handle mouse dragged if necessary
    }

    @Override
    public void naPretisnutoDugme(RoomView roomView, String key) {
        // Handle key presses if necessary
    }

    @Override
    public void naZoom(RoomView roomView, double zoomFactor) {
        // Handle zoom actions if necessary
    }
}
