package raf.draft.dsw.controller.actions.stateActions;

import raf.draft.dsw.controller.actions.AbstractRoomAction;
import raf.draft.dsw.gui.swing.MainFrame;

import java.awt.event.ActionEvent;

public class SelectStateAction extends AbstractRoomAction {
    public SelectStateAction() {

        putValue(NAME, "Selektuj");
        putValue(SMALL_ICON, loadIcon("/images/select.png"));
        putValue(SHORT_DESCRIPTION, "Selektuj elemente");

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Select State");
        MainFrame.getUniqueInstance().getMediator().getStateMenager().setSelectState();
    }
}
