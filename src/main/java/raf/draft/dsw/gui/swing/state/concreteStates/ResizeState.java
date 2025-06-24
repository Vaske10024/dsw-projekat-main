package raf.draft.dsw.gui.swing.state.concreteStates;

import raf.draft.dsw.controller.Command.RucniResizeCommand;
import raf.draft.dsw.gui.swing.RoomView;
import raf.draft.dsw.gui.swing.state.State;
import view.painters.Painter;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ResizeState implements State {

    private Map<Painter, Dimension> initialSizes = new HashMap<>();
    private Map<Painter, Point> initialPositions = new HashMap<>();

    @Override
    public void naKlikMisa(RoomView roomView, int x, int y) {
        if (roomView.getMediator().getSelectedNodes().size() == 1) {
            Painter selectedPainter = roomView.getMediator().getSelectedNodes().get(0);

            if (!initialSizes.containsKey(selectedPainter)) {
                initialSizes.put(selectedPainter, new Dimension(
                        selectedPainter.getRoomElement().getWidth(),
                        selectedPainter.getRoomElement().getHeight()
                ));
            }

            if (!initialPositions.containsKey(selectedPainter)) {
                initialPositions.put(selectedPainter, new Point(
                        selectedPainter.getRoomElement().getLokacijaX(),
                        selectedPainter.getRoomElement().getLokacijaY()
                ));
            }
        }
    }


    @Override
    public void naMouseDragged(RoomView roomView, int x, int y) {
        if (roomView.getMediator().getSelectedNodes().size() != 1) return;

        Painter selectedPainter = roomView.getMediator().getSelectedNodes().get(0);

        Point roomOffset = roomView.getRoomOffset();
        double scaleFactor = roomView.getScaleFactor();

        int transformedX = (int) ((x - roomOffset.x) / scaleFactor);
        int transformedY = (int) ((y - roomOffset.y) / scaleFactor);

        Rectangle bounds = selectedPainter.getBounds();
        int minWidth = 20;
        int minHeight = 20;

        int handleIndex = getResizeHandleIndex(bounds, transformedX, transformedY);

        if (handleIndex != -1) {
            resizePainter(selectedPainter, bounds, transformedX, transformedY, minWidth, minHeight, handleIndex);
            roomView.repaint();
        }
    }

    @Override
    public void naPretisnutoDugme(RoomView roomView, String key) {

    }

    @Override
    public void naZoom(RoomView roomView, double zoomFactor) {

    }

    public void naMouseReleased(RoomView roomView, int x, int y) {
        if (roomView.getMediator().getSelectedNodes().size() == 1) {
            Painter selectedPainter = roomView.getMediator().getSelectedNodes().get(0);

            Map<Painter, Dimension> newSizes = new HashMap<>();
            newSizes.put(selectedPainter, new Dimension(
                    selectedPainter.getRoomElement().getWidth(),
                    selectedPainter.getRoomElement().getHeight()
            ));

            Map<Painter, Point> newPositions = new HashMap<>();
            newPositions.put(selectedPainter, new Point(
                    selectedPainter.getRoomElement().getLokacijaX(),
                    selectedPainter.getRoomElement().getLokacijaY()
            ));

            if (!initialSizes.isEmpty() && !initialPositions.isEmpty()) {
                RucniResizeCommand resizeCommand = new RucniResizeCommand(
                        roomView,
                        new HashMap<>(initialSizes),
                        new HashMap<>(initialPositions),
                        new HashMap<>(newSizes),
                        new HashMap<>(newPositions)
                );
                roomView.getCommandHistory().executeCommand(resizeCommand);
            }


            initialSizes.clear();
            initialPositions.clear();
        }
    }

    private int getResizeHandleIndex(Rectangle bounds, int x, int y) {
        final int edgeThreshold = 10;

        if (Math.abs(x - bounds.x) <= edgeThreshold && Math.abs(y - bounds.y) <= edgeThreshold) return 0;
        if (Math.abs(x - (bounds.x + bounds.width)) <= edgeThreshold && Math.abs(y - bounds.y) <= edgeThreshold) return 1;
        if (Math.abs(x - bounds.x) <= edgeThreshold && Math.abs(y - (bounds.y + bounds.height)) <= edgeThreshold) return 2;
        if (Math.abs(x - (bounds.x + bounds.width)) <= edgeThreshold && Math.abs(y - (bounds.y + bounds.height)) <= edgeThreshold) return 3;

        return -1;
    }

    private void resizePainter(Painter selectedPainter, Rectangle bounds, int x, int y, int minWidth, int minHeight, int handleIndex) {
        switch (handleIndex) {
            case 0: // Gornji levi
                int newWidth0 = bounds.x + bounds.width - x;
                int newHeight0 = bounds.y + bounds.height - y;
                if (newWidth0 >= minWidth && newHeight0 >= minHeight) {
                    selectedPainter.setX(x);
                    selectedPainter.setY(y);
                    selectedPainter.setWidth(newWidth0);
                    selectedPainter.setHeight(newHeight0);
                }
                break;
            case 1: // Gornji desni
                int newWidth1 = x - bounds.x;
                if (newWidth1 >= minWidth) {
                    selectedPainter.setWidth(newWidth1);
                }
                break;
            case 2: // Donji levi
                int newHeight2 = y - bounds.y;
                if (newHeight2 >= minHeight) {
                    selectedPainter.setHeight(newHeight2);
                }
                break;
            case 3: // Donji desni
                int newWidth3 = x - bounds.x;
                int newHeight3 = y - bounds.y;
                if (newWidth3 >= minWidth && newHeight3 >= minHeight) {
                    selectedPainter.setWidth(newWidth3);
                    selectedPainter.setHeight(newHeight3);
                }
                break;
        }
    }

}
