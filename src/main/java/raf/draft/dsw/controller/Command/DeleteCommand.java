package raf.draft.dsw.controller.Command;

import raf.draft.dsw.model.Command;
import raf.draft.dsw.model.structures.Elementi.RoomElement;
import raf.draft.dsw.gui.swing.RoomView;
import raf.draft.dsw.gui.swing.JTree.controller.JTreeActions;
import view.painters.Painter;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import raf.draft.dsw.model.Command;
import raf.draft.dsw.model.structures.Elementi.RoomElement;
import raf.draft.dsw.gui.swing.RoomView;
import raf.draft.dsw.gui.swing.JTree.controller.JTreeActions;
import view.painters.Painter;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import raf.draft.dsw.model.Command;
import raf.draft.dsw.model.structures.Elementi.RoomElement;
import raf.draft.dsw.gui.swing.RoomView;
import raf.draft.dsw.gui.swing.JTree.controller.JTreeActions;
import view.painters.Painter;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import raf.draft.dsw.model.Command;
import raf.draft.dsw.model.structures.Elementi.RoomElement;
import raf.draft.dsw.gui.swing.RoomView;
import raf.draft.dsw.gui.swing.JTree.controller.JTreeActions;
import view.painters.Painter;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DeleteCommand implements Command {
    private final RoomView roomView;
    private final List<RoomElement> selectedElements;
    private final List<RoomElement> deletedElements;
    private final List<Painter> deletedPainters;
    private final JTreeActions jTreeActions;

    public DeleteCommand(RoomView roomView, List<RoomElement> selectedElements, JTreeActions jTreeActions) {
        this.roomView = roomView;
        this.selectedElements = new ArrayList<>(selectedElements);
        this.deletedElements = new ArrayList<>();
        this.deletedPainters = new ArrayList<>();
        this.jTreeActions = jTreeActions;
    }

    @Override
    public void execute() {
        deleteElements();
    }

    @Override
    public void undo() {
        for (RoomElement element : deletedElements) {
            roomView.getRoom().addChild(element);
            jTreeActions.addRoomElementToTree(roomView.getRoom(), element);
        }
        for (Painter painter : deletedPainters) {
            roomView.addPainter(painter);
        }
        roomView.revalidate();
        roomView.repaint();
    }

    @Override
    public void redo() {
        deleteElements();
    }

    private void deleteElements() {
        for (Painter painter : roomView.getPainters()) {
            RoomElement element = painter.getRoomElement();
            if (selectedElements.contains(element)) {
                deletedElements.add(element);
                deletedPainters.add(painter);
                roomView.getRoom().removeChild(element);
                jTreeActions.removeRoomElementFromTree(roomView.getRoom(), element);
            }
        }
        roomView.getPainters().removeAll(deletedPainters);
        roomView.revalidate();
        roomView.repaint();
    }
}
