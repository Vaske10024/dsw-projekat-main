package raf.draft.dsw.controller.actions.stateActions;

import raf.draft.dsw.controller.actions.AbstractRoomAction;
import raf.draft.dsw.gui.swing.MainFrame;

import java.awt.event.ActionEvent;

public class CopyPaseStateAction extends AbstractRoomAction {
    public CopyPaseStateAction() {

        putValue(NAME, "CopyPase");
        putValue(SMALL_ICON, loadIcon("/images/copypaste.png"));
        putValue(SHORT_DESCRIPTION, "Kopiraj akciju");

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        MainFrame.getUniqueInstance().getMediator().getStateMenager().setCopyPasteState();
    }

    @Override
    public boolean accept(Object sender) {
        return super.accept(sender);
    }
}
