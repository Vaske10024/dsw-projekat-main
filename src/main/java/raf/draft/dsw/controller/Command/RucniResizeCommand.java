package raf.draft.dsw.controller.Command;

import raf.draft.dsw.gui.swing.RoomView;
import raf.draft.dsw.model.Command;
import view.painters.Painter;

import java.awt.*;
import java.util.Map;

public class RucniResizeCommand implements Command {
    private RoomView roomView;
    private Map<Painter, Dimension> initialSizes;
    private Map<Painter, Point> initialPositions; // Početne pozicije
    private Map<Painter, Dimension> newSizes;
    private Map<Painter, Point> newPositions;     // Završne pozicije

    public RucniResizeCommand(RoomView roomView,
                              Map<Painter, Dimension> initialSizes,
                              Map<Painter, Point> initialPositions,
                              Map<Painter, Dimension> newSizes,
                              Map<Painter, Point> newPositions) {
        this.roomView = roomView;
        this.initialSizes = initialSizes;
        this.initialPositions = initialPositions;
        this.newSizes = newSizes;
        this.newPositions = newPositions;
    }

    @Override
    public void execute() {
        for (Painter painter : newSizes.keySet()) {
            Dimension newSize = newSizes.get(painter);
            Point newPosition = newPositions.get(painter);

            painter.getRoomElement().setWidth(newSize.width);
            painter.getRoomElement().setHeight(newSize.height);
            painter.getRoomElement().setLokacijaX(newPosition.x);
            painter.getRoomElement().setLokacijaY(newPosition.y);
        }
        roomView.repaint();
    }

    @Override
    public void undo() {
        for (Painter painter : initialSizes.keySet()) {
            Dimension initialSize = initialSizes.get(painter);
            Point initialPosition = initialPositions.get(painter);

            if (initialSize != null && initialPosition != null) {
                painter.getRoomElement().setWidth(initialSize.width);
                painter.getRoomElement().setHeight(initialSize.height);
                painter.getRoomElement().setLokacijaX(initialPosition.x);
                painter.getRoomElement().setLokacijaY(initialPosition.y);
            }
        }
        roomView.repaint();
        roomView.revalidate();
    }


    @Override
    public void redo() {
        execute();
    }
}
