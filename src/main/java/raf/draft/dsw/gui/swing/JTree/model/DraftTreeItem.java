package raf.draft.dsw.gui.swing.JTree.model;

import lombok.Getter;
import lombok.Setter;
import raf.draft.dsw.gui.swing.JTree.view.DraftTreeView;
import raf.draft.dsw.model.nodes.DraftNode;
import raf.draft.dsw.model.nodes.DraftNodeComposite;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;


@Getter
@Setter

public class DraftTreeItem extends DefaultMutableTreeNode {
    private DraftNode draftNode;

    public DraftTreeItem(DraftNode nodeModel) {
        this.draftNode = nodeModel;
    }

    @Override
    public String toString() {
        return draftNode.getNaziv();
    }

    public void setName(String name) {
        this.draftNode.setNaziv(name);
    }

    public DraftNode getDraftNode() {
        return draftNode;
    }

    public void setPutanja(String putanja){
        this.draftNode.setPutanjaDoProjekta(putanja);

    }

    public void setDraftNode(DraftNode draftNode) {
        this.draftNode = draftNode;
    }
    public void removeFromParentNode() {
        if (getParent() instanceof DraftTreeItem) {
            DraftTreeItem parentItem = (DraftTreeItem) getParent();
            DraftNodeComposite parentNode = (DraftNodeComposite) parentItem.getDraftNode();
            parentNode.removeChild(draftNode);
            parentItem.remove(this);
        }
    }

}

