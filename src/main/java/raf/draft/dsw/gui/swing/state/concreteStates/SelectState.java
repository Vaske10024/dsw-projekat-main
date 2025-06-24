package raf.draft.dsw.gui.swing.state.concreteStates;

import raf.draft.dsw.gui.swing.RoomView;
import raf.draft.dsw.gui.swing.state.State;
import raf.draft.dsw.model.structures.Room;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class SelectState implements State {


    public void naKlikMisa(RoomView roomView, int x, int y) {
        roomView.getMediator().getSelectedNodes().clear();
        view.painters.Painter selectedNode = null;

        // Dobijamo offset i skaliranje sobe
        Point roomOffset = roomView.getRoomOffset();
        double scaleFactor = roomView.getScaleFactor();

        // Transformacija koordinata miša u referentni sistem sobe
        int transformedX = (int) ((x - roomOffset.x) / scaleFactor);
        int transformedY = (int) ((y - roomOffset.y) / scaleFactor);

        // Prolazimo kroz sve painters i proveravamo koji je kliknut
        for (view.painters.Painter painter : roomView.getPainters()) {
            Rectangle bounds = painter.getBounds();

            // Proveravamo da li je painter kliknut
            if (bounds.contains(transformedX, transformedY)) {
                selectedNode = painter;
                break;
            }
        }

        if (selectedNode != null) {
            // Dodajemo selektovanog čvora u listu ako nije već dodat
            if (!roomView.getMediator().getSelectedNodes().contains(selectedNode)) {
                roomView.getMediator().getSelectedNodes().add(selectedNode);
                selectedNode.setSelected(true);
            } else {
                // Ako je već selektovan, uklonimo ga (toggling behavior)
                roomView.getMediator().getSelectedNodes().remove(selectedNode);
            }
        }


        roomView.repaint();
    }

    @Override
    public void naMouseDragged(RoomView roomView, int x, int y) {
        // Dobijamo offset i skaliranje
        Point roomOffset = roomView.getRoomOffset();
        double scaleFactor = roomView.getScaleFactor();

        // Transformacija koordinata miša
        int transformedX = (int) ((x - roomOffset.x) / scaleFactor);
        int transformedY = (int) ((y - roomOffset.y) / scaleFactor);

        // Početne tačke za selekciju
        int startX = (int) ((roomView.getStartingPoint().x - roomOffset.x) / scaleFactor);
        int startY = (int) ((roomView.getStartingPoint().y - roomOffset.y) / scaleFactor);

        // Dimenzije selekcionog pravougaonika
        int width = transformedX - startX;
        int height = transformedY - startY;

        // Ažuriranje koordinata za negativne dimenzije
        if (width < 0) {
            startX += width;
            width = -width;
        }
        if (height < 0) {
            startY += height;
            height = -height;
        }

        Rectangle selectionRectangle = new Rectangle(startX, startY, width, height);

        // Nova lista za selektovane elemente
        ArrayList<view.painters.Painter> selectedPainters = new ArrayList<>();

        // Prolazimo kroz sve painters i proveravamo da li su u selekciji
        for (view.painters.Painter painter : roomView.getPainters()) {
            Rectangle bounds = painter.getBounds();

            if (selectionRectangle.intersects(bounds)) {
                if (!selectedPainters.contains(painter)) {
                    selectedPainters.add(painter);
                    painter.setSelected(true);
                }
            } else {
                painter.setSelected(false);
            }
        }

        // Ažuriramo selektovane elemente
        roomView.getMediator().getSelectedNodes().clear();
        roomView.getMediator().getSelectedNodes().addAll(selectedPainters);

        roomView.setSelectionRectangle(selectionRectangle);
        roomView.repaint();

    }
    @Override
    public void naPretisnutoDugme(RoomView roomView, String key) {

    }

    @Override
    public void naZoom(RoomView roomView, double zoomFactor) {

    }


    public void naMouseReleased(RoomView roomView) {
        roomView.setSelectionRectangle(null);
        roomView.revalidate();
        roomView.repaint();
    }
}
