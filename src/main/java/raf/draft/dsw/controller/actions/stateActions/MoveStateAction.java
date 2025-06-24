package raf.draft.dsw.controller.actions.stateActions;

import com.sun.tools.javac.Main;
import raf.draft.dsw.controller.actions.AbstractRoomAction;
import raf.draft.dsw.gui.swing.MainFrame;

import java.awt.event.ActionEvent;

public class MoveStateAction extends AbstractRoomAction {
    public MoveStateAction() {

        putValue(NAME, "Move");
        putValue(SMALL_ICON, loadIcon("/images/move.png"));
        putValue(SHORT_DESCRIPTION, "Pomeraj");

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Move state");
        MainFrame.getUniqueInstance().getMediator().getStateMenager().setMoveState();
    }
}
