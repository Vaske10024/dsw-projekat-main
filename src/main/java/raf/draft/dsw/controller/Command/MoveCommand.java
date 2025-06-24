package raf.draft.dsw.controller.Command;

import raf.draft.dsw.gui.swing.RoomView;
import raf.draft.dsw.model.Command;
import view.painters.Painter;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

public class MoveCommand implements Command {
    private RoomView roomView;
    private Map<Painter, Point> initialPositions;
    private Map<Painter, Point> newPositions;

    public MoveCommand(RoomView roomView, Map<Painter, Point> initialPositions, Map<Painter, Point> newPositions) {
        this.roomView = roomView;
        // Duboka kopija mapa za stabilnost undo/redo sistema
        this.initialPositions = new HashMap<>(initialPositions);
        this.newPositions = new HashMap<>(newPositions);
    }

    @Override
    public void execute() {
        for (Painter painter : newPositions.keySet()) {
            Point newPosition = newPositions.get(painter);
            painter.setX(newPosition.x);
            painter.setY(newPosition.y);
        }
        roomView.repaint();
    }

    @Override
    public void undo() {
        for (Painter painter : initialPositions.keySet()) {
            Point initialPosition = initialPositions.get(painter);
            painter.setX(initialPosition.x);
            painter.setY(initialPosition.y);
        }
        roomView.repaint();
    }

    @Override
    public void redo() {
        execute();
    }
}
