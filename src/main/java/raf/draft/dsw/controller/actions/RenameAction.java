package raf.draft.dsw.controller.actions;

import raf.draft.dsw.controller.messagegenerator.MessageGenerator;
import raf.draft.dsw.gui.swing.JTree.model.DraftTreeItem;
import raf.draft.dsw.gui.swing.MainFrame;
import raf.draft.dsw.model.messages.MessageType;
import raf.draft.dsw.model.structures.ProjectExplorer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class RenameAction extends AbstractRoomAction{
    public RenameAction() {
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
        putValue(SMALL_ICON, loadIcon("/images/rename.png"));
        putValue(NAME, "Rename");
        putValue(SHORT_DESCRIPTION, "Rename");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        DraftTreeItem selected = (DraftTreeItem) MainFrame.getUniqueInstance().getDraftTreeImplementation().getjTreeActions().getSelectedNode();
        if(selected == null || selected.getDraftNode() instanceof ProjectExplorer){
            MessageGenerator messageGenerator = new MessageGenerator();
            messageGenerator.generateMessage("Ne mozete menjati ime project exploreru",MessageType.GRESKA);
            return;
        }
        MainFrame.getUniqueInstance().getDraftTreeImplementation().getjTreeActions().renameNode(selected);
    }
}
