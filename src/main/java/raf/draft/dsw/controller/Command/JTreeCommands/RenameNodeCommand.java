package raf.draft.dsw.controller.Command.JTreeCommands;

import raf.draft.dsw.gui.swing.JTree.controller.JTreeActions;
import raf.draft.dsw.gui.swing.JTree.model.DraftTreeItem;
import raf.draft.dsw.model.Command;

public class RenameNodeCommand implements Command {
    private DraftTreeItem node;
    private String oldName;
    private String newName;
    private JTreeActions jTreeActions;

    public RenameNodeCommand(DraftTreeItem node, String newName, JTreeActions jTreeActions) {
        this.node = node;
        this.oldName = node.getDraftNode().getNaziv();
        this.newName = newName;
        this.jTreeActions = jTreeActions;
    }

    @Override
    public void execute() {
        jTreeActions.renameNode(node);
    }

    @Override
    public void undo() {
        node.getDraftNode().setNaziv(oldName);

    }

    @Override
    public void redo() {

    }
}
