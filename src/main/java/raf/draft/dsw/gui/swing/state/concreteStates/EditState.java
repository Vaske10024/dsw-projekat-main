package raf.draft.dsw.gui.swing.state.concreteStates;

import com.sun.tools.javac.Main;
import raf.draft.dsw.controller.Command.ResizeCommand;
import raf.draft.dsw.gui.swing.MainFrame;
import raf.draft.dsw.gui.swing.PopUpWindows.RoomElementSize;
import raf.draft.dsw.gui.swing.RoomView;
import raf.draft.dsw.gui.swing.state.State;
import raf.draft.dsw.model.structures.Elementi.RoomElement;
import raf.draft.dsw.model.structures.Room;

import java.awt.*;
import java.awt.image.BufferedImage;

import raf.draft.dsw.gui.swing.PopUpWindows.RoomElementSize;
import raf.draft.dsw.gui.swing.RoomView;
import raf.draft.dsw.gui.swing.state.State;
import raf.draft.dsw.model.structures.Elementi.RoomElement;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class EditState implements State {

    @Override
    public void naKlikMisa(RoomView roomView, int x, int y) {
        if (roomView.getMediator().getSelectedNodes().size() == 1) {
            RoomElement roomElement = roomView.getMediator().getSelectedNodes().get(0).getRoomElement();

            RoomElementSize roomElementSize = new RoomElementSize(roomElement.getWidth(), roomElement.getHeight());
            roomElementSize = RoomElementSize.promptForSize();
            if (roomElementSize == null) {
                System.out.println("Molimo unesite pravilan upis");
                return;
            }

            int newWidth = (int) (roomElementSize.getWidth() * 2.7);
            int newHeight = (int) (roomElementSize.getHeight() * 2.7);

            Rectangle newBounds = new Rectangle(
                    roomElement.getLokacijaX(),
                    roomElement.getLokacijaY(),
                    newWidth,
                    newHeight
            );

            // Proveri da li novo povećanje izaziva preklapanje
            for (view.painters.Painter painter : roomView.getPainters()) {
                if (!painter.getRoomElement().equals(roomElement) && newBounds.intersects(painter.getBounds())) {
                    System.out.println("Povećanje elementa izaziva preklapanje sa drugim elementom.");
                    return;
                }
            }

            // Proveri da li novo povećanje izlazi iz granica sobe
            if (newBounds.getX() < 0 || newBounds.getY() < 0 ||
                    newBounds.getMaxX() > roomView.getRoom().getWidth() ||
                    newBounds.getMaxY() > roomView.getRoom().getHeight()) {
                System.out.println("Povećanje elementa izlazi iz granica sobe.");
                return;
            }

            // Zapamti početne veličine
            Map<view.painters.Painter, Dimension> initialSizes = new HashMap<>();
            initialSizes.put(roomView.getMediator().getSelectedNodes().get(0),
                    new Dimension(roomElement.getWidth(), roomElement.getHeight()));

            // Postavi novu veličinu
            roomElement.setWidth(newWidth);
            roomElement.setHeight(newHeight);

            // Zapamti nove veličine
            Map<view.painters.Painter, Dimension> newSizes = new HashMap<>();
            newSizes.put(roomView.getMediator().getSelectedNodes().get(0),
                    new Dimension(newWidth, newHeight));

            // Kreiraj i izvrši ResizeCommand
            ResizeCommand resizeCommand = new ResizeCommand(roomView, initialSizes, newSizes);
            roomView.getCommandHistory().executeCommand(resizeCommand);
        }
        roomView.repaint();
    }

    @Override
    public void naMouseDragged(RoomView roomView, int x, int y) {
        // Nije implementirano
    }

    @Override
    public void naPretisnutoDugme(RoomView roomView, String key) {
        // Nije implementirano
    }

    @Override
    public void naZoom(RoomView roomView, double zoomFactor) {
        // Nije implementirano
    }
}
