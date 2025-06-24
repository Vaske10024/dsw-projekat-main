package raf.draft.dsw.controller.actions.stateActions;

import raf.draft.dsw.controller.actions.AbstractRoomAction;
import raf.draft.dsw.gui.swing.MainFrame;

import java.awt.event.ActionEvent;

public class ResizeStateAction extends AbstractRoomAction {
    public ResizeStateAction() {

        putValue(NAME, "Resize");
        putValue(SMALL_ICON, loadIcon("/images/resize.png"));
        putValue(SHORT_DESCRIPTION, "Risajzuj");

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("ResizeStateAction");
        MainFrame.getUniqueInstance().getMediator().getStateMenager().setResizeState();
    }
}
