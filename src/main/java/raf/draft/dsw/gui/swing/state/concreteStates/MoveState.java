package raf.draft.dsw.gui.swing.state.concreteStates;

import com.sun.tools.javac.Main;
import raf.draft.dsw.controller.Command.MoveCommand;
import raf.draft.dsw.gui.swing.MainFrame;
import raf.draft.dsw.gui.swing.RoomView;
import raf.draft.dsw.gui.swing.state.State;
import raf.draft.dsw.model.structures.Elementi.RoomElement;
import view.painters.Painter;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Map;



import raf.draft.dsw.gui.swing.RoomView;
import raf.draft.dsw.gui.swing.state.State;
import raf.draft.dsw.model.structures.Elementi.RoomElement;
import view.painters.Painter;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Map;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Map;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Map;

public class MoveState implements State {
    private Point startDragPoint; // Initial mouse position
    private Point roomInitialOffset; // Initial offset of the room when drag starts
    private Map<Painter, Point> initialPositions = new HashMap<>(); // Initial positions for all selected painters

    public void naMousePressed(RoomView roomView, int x, int y) {
        startDragPoint = new Point(x, y);
        roomInitialOffset = new Point(roomView.getRoomOffset());
        initialPositions.clear();

        for (Painter selectedPainter : roomView.getMediator().getSelectedNodes()) {
            if (selectedPainter != null) {
                initialPositions.put(selectedPainter, new Point(selectedPainter.getX(), selectedPainter.getY()));
            }
        }
    }


    public void naMouseReleased(RoomView roomView, int x, int y) {
        if (!initialPositions.isEmpty()) {
            Map<Painter, Point> newPositions = new HashMap<>();
            for (Painter selectedPainter : roomView.getMediator().getSelectedNodes()) {
                if (selectedPainter != null) {
                    newPositions.put(selectedPainter, new Point(selectedPainter.getX(), selectedPainter.getY()));
                }
            }

            // Kreiraj novu komandu sa početnim i krajnjim pozicijama
            MoveCommand moveCommand = new MoveCommand(roomView, initialPositions, newPositions);
            roomView.getCommandHistory().executeCommand(moveCommand);
            // Resetuj početne pozicije
            initialPositions.clear();
        }
    }


    @Override
    public void naKlikMisa(RoomView roomView, int x, int y) {
        // Handle mouse clicks if necessary
    }

    @Override
    public void naMouseDragged(RoomView roomView, int x, int y) {
        double scaleFactor = roomView.getScaleFactor();

        if (roomView.getMediator().getSelectedNodes().isEmpty()) {
            int newOffsetX = roomInitialOffset.x + (int) ((x - startDragPoint.x) / scaleFactor);
            int newOffsetY = roomInitialOffset.y + (int) ((y - startDragPoint.y) / scaleFactor);
            roomView.setOffsetSobe(new Point(newOffsetX, newOffsetY));
            roomView.repaint();
        } else {
            boolean allMovementValid = true;

            for (Painter selectedPainter : roomView.getMediator().getSelectedNodes()) {
                if (selectedPainter != null) {
                    Point initialPosition = initialPositions.get(selectedPainter);
                    int newX = initialPosition.x + (int) ((x - startDragPoint.x) / scaleFactor);
                    int newY = initialPosition.y + (int) ((y - startDragPoint.y) / scaleFactor);

                    Rectangle2D newBounds = getTransformedBounds(newX, newY, selectedPainter);

                    if (isOutOfRoomBounds(newBounds, roomView) || isOverlappingWithOtherPainters(newBounds, roomView)) {
                        allMovementValid = false;
                        break;
                    }
                }
            }

            if (allMovementValid) {
                for (Painter selectedPainter : roomView.getMediator().getSelectedNodes()) {
                    if (selectedPainter != null) {
                        Point initialPosition = initialPositions.get(selectedPainter);
                        int newX = initialPosition.x + (int) ((x - startDragPoint.x) / roomView.getScaleFactor());
                        int newY = initialPosition.y + (int) ((y - startDragPoint.y) / roomView.getScaleFactor());

                        Rectangle2D newBounds = getTransformedBounds(newX, newY, selectedPainter);
                        snapToRoomEdges(newBounds, roomView);

                        selectedPainter.getRoomElement().setLokacijaX((int) (newBounds.getX()));
                        selectedPainter.getRoomElement().setLokacijaY((int) (newBounds.getY()));
                    }
                }
            }

            roomView.repaint();
        }
    }

    @Override
    public void naPretisnutoDugme(RoomView roomView, String key) {
        // Handle key presses if necessary
    }

    @Override
    public void naZoom(RoomView roomView, double zoomFactor) {
        // Handle zoom actions if necessary
    }

    private boolean isOutOfRoomBounds(Rectangle2D bounds, RoomView roomView) {
        int roomWidth = roomView.getRoom().getWidth();
        int roomHeight = roomView.getRoom().getHeight();

        return bounds.getMinX() < 0 || bounds.getMinY() < 0 || bounds.getMaxX() > roomWidth || bounds.getMaxY() > roomHeight;
    }

    private boolean isOverlappingWithOtherPainters(Rectangle2D bounds, RoomView roomView) {
        for (Painter otherPainter : roomView.getPainters()) {
            if (!roomView.getMediator().getSelectedNodes().contains(otherPainter)) {
                Rectangle2D otherBounds = getTransformedBounds(otherPainter.getRoomElement().getLokacijaX(), otherPainter.getRoomElement().getLokacijaY(), otherPainter);
                if (bounds.intersects(otherBounds)) {
                    return true;
                }
            }
        }
        return false;
    }

    private Rectangle2D getTransformedBounds(int x, int y, Painter painter) {
        int width = painter.getRoomElement().getWidth();
        int height = painter.getRoomElement().getHeight();
        int rotation = painter.getRoomElement().getRotacija();

        int centerX = x + width / 2;
        int centerY = y + height / 2;

        Rectangle bounds = new Rectangle(x, y, width, height);

        AffineTransform transform = new AffineTransform();
        transform.rotate(Math.toRadians(rotation), centerX, centerY);

        Shape transformedBounds = transform.createTransformedShape(bounds);
        return transformedBounds.getBounds2D();
    }

    private void snapToRoomEdges(Rectangle2D bounds, RoomView roomView) {
        int snapThreshold = 10;
        int roomWidth = roomView.getRoom().getWidth();
        int roomHeight = roomView.getRoom().getHeight();

        if (bounds.getMinX() < snapThreshold) {
            bounds.setRect(0, bounds.getY(), bounds.getWidth(), bounds.getHeight());
        }
        if (bounds.getMinY() < snapThreshold) {
            bounds.setRect(bounds.getX(), 0, bounds.getWidth(), bounds.getHeight());
        }
        if (roomWidth - bounds.getMaxX() < snapThreshold) {
            bounds.setRect(roomWidth - bounds.getWidth(), bounds.getY(), bounds.getWidth(), bounds.getHeight());
        }
        if (roomHeight - bounds.getMaxY() < snapThreshold) {
            bounds.setRect(bounds.getX(), roomHeight - bounds.getHeight(), bounds.getWidth(), bounds.getHeight());
        }
    }
}
