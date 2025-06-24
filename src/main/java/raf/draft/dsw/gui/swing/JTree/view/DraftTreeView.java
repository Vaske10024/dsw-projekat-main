package raf.draft.dsw.gui.swing.JTree.view;

import raf.draft.dsw.gui.swing.JTree.controller.DraftTreeCellEditor;
import raf.draft.dsw.gui.swing.JTree.controller.DraftTreeSelectionListener;
import raf.draft.dsw.gui.swing.JTree.controller.JTreeActions;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;

public class DraftTreeView extends JTree {

    private JTreeActions actions;
    DraftTreeCellRenderer ruTreeCellRenderer = new DraftTreeCellRenderer();


    public DraftTreeView(DefaultTreeModel defaultTreeModel, JTreeActions jTreeActions) {
        super(defaultTreeModel);
        setCellRenderer(ruTreeCellRenderer);
        if(defaultTreeModel==null){
            throw new NullPointerException("defaultTreeModel is null");
        }
        this.actions=jTreeActions;

        setModel(defaultTreeModel);
        DraftTreeCellRenderer ruTreeCellRenderer = new DraftTreeCellRenderer();
        addTreeSelectionListener(new DraftTreeSelectionListener());
        setCellEditor(new DraftTreeCellEditor(this, ruTreeCellRenderer,actions));
        setCellRenderer(ruTreeCellRenderer);
        setEditable(true);
    }

}