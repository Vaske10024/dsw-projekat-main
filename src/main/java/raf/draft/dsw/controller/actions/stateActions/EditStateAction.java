package raf.draft.dsw.controller.actions.stateActions;

import raf.draft.dsw.controller.actions.AbstractRoomAction;
import raf.draft.dsw.gui.swing.MainFrame;
import raf.draft.dsw.gui.swing.PopUpWindows.RoomElementSize;

import java.awt.event.ActionEvent;

public class EditStateAction extends AbstractRoomAction {
    public EditStateAction() {

        putValue(NAME, "Edit");
        putValue(SMALL_ICON, loadIcon("/images/edit.png"));
        putValue(SHORT_DESCRIPTION, "Edituj nesto");

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(MainFrame.getUniqueInstance().getMediator().getSelectedNodes().isEmpty()){
            MainFrame.getUniqueInstance().getMediator().getStateMenager().setEditRoomState();
        }
        else MainFrame.getUniqueInstance().getMediator().getStateMenager().setEditState();
    }
}
