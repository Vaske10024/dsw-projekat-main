package raf.draft.dsw.controller.Command;

import raf.draft.dsw.gui.swing.RoomView;
import raf.draft.dsw.model.Command;

import java.awt.Point;
import java.util.Map;

import java.util.Map;


import raf.draft.dsw.gui.swing.RoomView;
import raf.draft.dsw.model.Command;
import view.painters.Painter;

import java.util.HashMap;
import java.util.Map;

public class RotateCommand implements Command {
    private RoomView roomView;
    private Map<Painter, Integer> initialRotations;
    private Map<Painter, Integer> newRotations;

    public RotateCommand(RoomView roomView, Map<Painter, Integer> initialRotations, Map<Painter, Integer> newRotations) {
        this.roomView = roomView;
        // Duboka kopija mapa za stabilnost undo/redo sistema
        this.initialRotations = new HashMap<>(initialRotations);
        this.newRotations = new HashMap<>(newRotations);
    }

    @Override
    public void execute() {
        for (Painter painter : newRotations.keySet()) {
            int newRotation = newRotations.get(painter);
            painter.getRoomElement().setRotacija(newRotation);
        }
        roomView.repaint();
    }

    @Override
    public void undo() {
        for (Painter painter : initialRotations.keySet()) {
            int initialRotation = initialRotations.get(painter);
            painter.getRoomElement().setRotacija(initialRotation);
        }
        roomView.repaint();
    }

    @Override
    public void redo() {
        execute();
    }
}


