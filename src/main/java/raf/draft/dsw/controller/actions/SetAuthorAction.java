package raf.draft.dsw.controller.actions;

import raf.draft.dsw.controller.messagegenerator.MessageGenerator;
import raf.draft.dsw.gui.swing.JTree.model.DraftTreeItem;
import raf.draft.dsw.gui.swing.MainFrame;
import raf.draft.dsw.model.messages.MessageType;
import raf.draft.dsw.model.structures.Project;
import raf.draft.dsw.model.structures.ProjectExplorer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class SetAuthorAction extends AbstractRoomAction{
    public SetAuthorAction() {
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
        putValue(SMALL_ICON, loadIcon("/images/addAuthor.png"));
        putValue(NAME, "Set Author");
        putValue(SHORT_DESCRIPTION, "Set Author");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        DraftTreeItem selected = (DraftTreeItem) MainFrame.getUniqueInstance().getDraftTreeImplementation().getjTreeActions().getSelectedNode();
        if(selected == null || !(selected.getDraftNode() instanceof Project)){
            MessageGenerator messageGenerator = new MessageGenerator();
            messageGenerator.generateMessage("Samo Projekat ima autora", MessageType.GRESKA);
            return;
        }
        MainFrame.getUniqueInstance().getDraftTreeImplementation().getjTreeActions().setAutor(selected);
    }
}
