package raf.draft.dsw.controller.actions.stateActions;

import com.sun.tools.javac.Main;
import raf.draft.dsw.controller.actions.AbstractRoomAction;
import raf.draft.dsw.gui.swing.JTree.controller.JTreeActions;
import raf.draft.dsw.gui.swing.MainFrame;
import raf.draft.dsw.gui.swing.RoomView;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import raf.draft.dsw.controller.actions.AbstractRoomAction;
import raf.draft.dsw.gui.swing.JTree.controller.JTreeActions;
import raf.draft.dsw.gui.swing.MainFrame;
import raf.draft.dsw.gui.swing.RoomView;
import raf.draft.dsw.model.structures.Elementi.RoomElement;
import raf.draft.dsw.controller.Command.DeleteCommand;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class DeleteStateAction extends AbstractRoomAction {

    private JTreeActions jTreeActions;

    public DeleteStateAction() {
        putValue(NAME, "Delete");
        putValue(SMALL_ICON, loadIcon("/images/delete2.jpg"));
        putValue(SHORT_DESCRIPTION, "Obrisi element");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        MainFrame.getUniqueInstance().getMediator().getStateMenager().setDeleteState();

        this.jTreeActions = MainFrame.getUniqueInstance().getDraftTreeImplementation().getjTreeActions();
        RoomView roomView = MainFrame.getUniqueInstance().getMediator().getRoomView();

        List<RoomElement> selectedElements = new ArrayList<>();
        for (view.painters.Painter painter : new ArrayList<>(roomView.getMediator().getSelectedNodes())) {
            selectedElements.add(painter.getRoomElement());
        }

        if (!selectedElements.isEmpty()) {
            DeleteCommand deleteCommand = new DeleteCommand(roomView, selectedElements, jTreeActions);
            roomView.getCommandHistory().executeCommand(deleteCommand);

            roomView.getMediator().getSelectedNodes().clear();
            roomView.revalidate();
            roomView.repaint();
        }
    }
}

