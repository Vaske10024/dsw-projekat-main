package raf.draft.dsw.gui.swing.state.concreteStates;

import raf.draft.dsw.controller.Command.CopyPasteCommand;
import raf.draft.dsw.gui.swing.JTree.controller.JTreeActions;
import raf.draft.dsw.gui.swing.MainFrame;
import raf.draft.dsw.gui.swing.RoomElementFactory.RoomElementFactory;
import raf.draft.dsw.gui.swing.RoomView;
import raf.draft.dsw.gui.swing.state.State;
import raf.draft.dsw.model.structures.Elementi.RoomElement;
import view.painters.Painter;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class CopyPasteState implements State {
    private ArrayList<RoomElement> copiedElements = new ArrayList<>();
    private RoomElementFactory roomElementFactory;
    private JTreeActions jTreeActions;

    public CopyPasteState(AddState addState) {
        if (addState != null) {
            this.roomElementFactory = addState.getRoomElementFactory();
        }
    }

    @Override
    public void naKlikMisa(RoomView roomView, int x, int y) {
        this.jTreeActions = MainFrame.getUniqueInstance().getDraftTreeImplementation().getjTreeActions();
        if (roomView != null && roomView.getMediator() != null && !roomView.getMediator().getSelectedNodes().isEmpty()) {
            copiedElements.clear();
            for (Painter painter : roomView.getMediator().getSelectedNodes()) {
                RoomElement element = painter.getRoomElement();
                if (element != null) {
                    copiedElements.add(element.Clone());
                }
            }
            System.out.println("Elements copied: " + copiedElements.size());
        }
    }

    @Override
    public void naMouseDragged(RoomView roomView, int x, int y) {
    }

    @Override
    public void naPretisnutoDugme(RoomView roomView, String key) {
        if (key != null && (key.equalsIgnoreCase("Ctrl+V") || key.equalsIgnoreCase("Command+V"))) {
            roomView.getCommandHistory().executeCommand(new CopyPasteCommand(copiedElements, roomView, roomElementFactory, jTreeActions, 10, 0));
        }
    }

    @Override
    public void naZoom(RoomView roomView, double zoomFactor) {
    }
}
