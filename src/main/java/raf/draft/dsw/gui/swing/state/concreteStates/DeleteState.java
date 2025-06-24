package raf.draft.dsw.gui.swing.state.concreteStates;

import com.sun.tools.javac.Main;
import raf.draft.dsw.controller.Command.DeleteCommand;
import raf.draft.dsw.gui.swing.JTree.controller.JTreeActions;
import raf.draft.dsw.gui.swing.MainFrame;
import raf.draft.dsw.gui.swing.RoomView;
import raf.draft.dsw.gui.swing.state.State;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

import raf.draft.dsw.gui.swing.JTree.controller.JTreeActions;
import raf.draft.dsw.gui.swing.MainFrame;
import raf.draft.dsw.gui.swing.RoomView;
import raf.draft.dsw.gui.swing.state.State;

import java.awt.*;

import raf.draft.dsw.gui.swing.JTree.controller.JTreeActions;
import raf.draft.dsw.gui.swing.MainFrame;
import raf.draft.dsw.gui.swing.RoomView;
import raf.draft.dsw.gui.swing.state.State;
import raf.draft.dsw.model.structures.Elementi.RoomElement;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import raf.draft.dsw.gui.swing.JTree.controller.JTreeActions;
import raf.draft.dsw.gui.swing.MainFrame;
import raf.draft.dsw.gui.swing.RoomView;
import raf.draft.dsw.gui.swing.state.State;
import raf.draft.dsw.model.structures.Elementi.RoomElement;
import view.painters.Painter;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import raf.draft.dsw.gui.swing.JTree.controller.JTreeActions;
import raf.draft.dsw.gui.swing.MainFrame;
import raf.draft.dsw.gui.swing.RoomView;
import raf.draft.dsw.gui.swing.state.State;
import raf.draft.dsw.model.structures.Elementi.RoomElement;
import view.painters.Painter;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DeleteState implements State {

    private JTreeActions jTreeActions;

    @Override
    public void naKlikMisa(RoomView roomView, int x, int y) {
        this.jTreeActions = MainFrame.getUniqueInstance().getDraftTreeImplementation().getjTreeActions();

        Point clickPoint = new Point(x, y);
        List<RoomElement> selectedElements = new ArrayList<>();

        Point roomOffset = roomView.getRoomOffset();
        double scaleFactor = roomView.getScaleFactor();

        int transformedX = (int) ((clickPoint.x - roomOffset.x) / scaleFactor);
        int transformedY = (int) ((clickPoint.y - roomOffset.y) / scaleFactor);

        for (Painter painter : roomView.getPainters()) {
            Rectangle bounds = painter.getBounds();
            if (bounds.contains(transformedX, transformedY)) {
                selectedElements.add(painter.getRoomElement());
            }
        }

        if (!selectedElements.isEmpty()) {
            DeleteCommand deleteCommand = new DeleteCommand(roomView, selectedElements, jTreeActions);
            roomView.getCommandHistory().executeCommand(deleteCommand);
        }
    }

    @Override
    public void naMouseDragged(RoomView roomView, int x, int y) {}

    @Override
    public void naPretisnutoDugme(RoomView roomView, String key) {}

    @Override
    public void naZoom(RoomView roomView, double zoomFactor) {}
}

