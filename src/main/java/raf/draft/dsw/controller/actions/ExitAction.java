package raf.draft.dsw.controller.actions;

import raf.draft.dsw.controller.actions.AbstractRoomAction;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class ExitAction extends AbstractRoomAction {
    public ExitAction(){
        //bitno
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
        putValue(SMALL_ICON, loadIcon("/images/exit.png"));
        putValue(NAME, "Exit");
        putValue(SHORT_DESCRIPTION, "Exit");
    }

    //deo koda za ucitavanje ikonice...

    @Override
    public void actionPerformed(ActionEvent e) {
        System.exit(0);
    }

}
