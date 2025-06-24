package raf.draft.dsw.controller.actions;



import raf.draft.dsw.gui.swing.JTree.model.DraftTreeItem;
import raf.draft.dsw.gui.swing.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class AddProjectAction extends AbstractRoomAction{

    public AddProjectAction() {
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        putValue(SMALL_ICON, loadIcon("/images/add.png"));
        putValue(NAME, "New Project");
        putValue(SHORT_DESCRIPTION, "New Project");}

    @Override
    public void actionPerformed(ActionEvent e) {
        DraftTreeItem selected = (DraftTreeItem) MainFrame.getUniqueInstance().getDraftTreeImplementation().getjTreeActions().getSelectedNode();
        if(selected==null){
            return;
        }
        MainFrame.getUniqueInstance().getDraftTreeImplementation().getjTreeActions().addChild(selected);

    }


}
