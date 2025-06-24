package raf.draft.dsw.controller.Command;

import raf.draft.dsw.gui.swing.RoomView;
import raf.draft.dsw.model.Command;

import java.awt.*;
import java.util.Map;

public class ResizeCommand implements Command {
    private RoomView roomView;
    private Map<view.painters.Painter, Dimension> initialSizes;
    private Map<view.painters.Painter, Dimension> newSizes;

    public ResizeCommand(RoomView roomView, Map<view.painters.Painter, Dimension> initialSizes, Map<view.painters.Painter, Dimension> newSizes) {
        this.roomView = roomView;
        this.initialSizes = initialSizes;
        this.newSizes = newSizes;
    }

    @Override
    public void execute() {
        for (view.painters.Painter painter : newSizes.keySet()) {
            Dimension newSize = newSizes.get(painter);
            painter.getRoomElement().setWidth(newSize.width);
            painter.getRoomElement().setHeight(newSize.height);
        }
        roomView.repaint();
    }

    @Override
    public void undo() {
        for (view.painters.Painter painter : initialSizes.keySet()) {
            Dimension initialSize = initialSizes.get(painter);
            if (initialSize != null) {
                painter.getRoomElement().setWidth(initialSize.width);
                painter.getRoomElement().setHeight(initialSize.height);
            }
        }
        roomView.revalidate(); // Ažurira layout
        roomView.repaint();    // Ponovno iscrtavanje
    }


    @Override
    public void redo() {
        execute();
    }
}
