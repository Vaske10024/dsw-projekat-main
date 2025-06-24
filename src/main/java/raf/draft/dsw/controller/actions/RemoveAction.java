package raf.draft.dsw.controller.actions;

import raf.draft.dsw.gui.swing.JTree.model.DraftTreeItem;
import raf.draft.dsw.gui.swing.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class RemoveAction extends AbstractRoomAction{
    public RemoveAction() {
       putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke((char) KeyEvent.VK_DELETE));
        putValue(SMALL_ICON, loadIcon("/images/delete2.jpg"));
        putValue(NAME, "Remove");
        putValue(SHORT_DESCRIPTION, "Remove");

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        DraftTreeItem selected = (DraftTreeItem) MainFrame.getUniqueInstance().getDraftTreeImplementation().getjTreeActions().getSelectedNode();
        if(selected==null){
            return;
        }
        MainFrame.getUniqueInstance().getDraftTreeImplementation().getjTreeActions().removeNode();
    }
}
