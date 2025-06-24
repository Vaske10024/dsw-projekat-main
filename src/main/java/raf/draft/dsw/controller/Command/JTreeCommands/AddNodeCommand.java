package raf.draft.dsw.controller.Command.JTreeCommands;

import raf.draft.dsw.gui.swing.JTree.controller.JTreeActions;
import raf.draft.dsw.gui.swing.JTree.model.DraftTreeItem;
import raf.draft.dsw.model.Command;
import raf.draft.dsw.model.nodes.DraftNode;

public class AddNodeCommand implements Command {
    private DraftTreeItem parent;
    private DraftNode child;
    private JTreeActions jTreeActions;

    public AddNodeCommand(DraftTreeItem parent, DraftNode child, JTreeActions jTreeActions) {
        this.parent = parent;
        this.child = child;
        this.jTreeActions = jTreeActions;
    }

    @Override
    public void execute() {
        jTreeActions.addNodeToTree(child, parent.getDraftNode());
    }

    @Override
    public void undo() {
        jTreeActions.removeNode();
    }

    @Override
    public void redo() {

    }
}
