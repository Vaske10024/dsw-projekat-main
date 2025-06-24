package raf.draft.dsw.controller.actions.stateActions;

import raf.draft.dsw.controller.actions.AbstractRoomAction;
import raf.draft.dsw.gui.swing.MainFrame;

import java.awt.event.ActionEvent;

public class ZoomStateAction extends AbstractRoomAction {
    public ZoomStateAction() {

        putValue(NAME, "Zoom");
        putValue(SMALL_ICON, loadIcon("/images/zoom2.png"));
        putValue(SHORT_DESCRIPTION, "Zumiraj");

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        MainFrame.getUniqueInstance().getMediator().getStateMenager().setZoomState();

    }
}
