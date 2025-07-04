package raf.draft.dsw.gui.swing.JTree.controller;

import raf.draft.dsw.gui.swing.JTree.model.DraftTreeItem;
import raf.draft.dsw.model.structures.Project;
import raf.draft.dsw.model.structures.ProjectExplorer;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellEditor;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.EventObject;

public class DraftTreeCellEditor extends DefaultTreeCellEditor implements ActionListener
{
    private Object clickedOn= null;
    private JTextField edit=null;
    private JTreeActions jTreeActions;



    public DraftTreeCellEditor(JTree tree, DefaultTreeCellRenderer renderer, JTreeActions actions) {
        super(tree, renderer);
        jTreeActions=actions;
    }

    public Component getTreeCellEditorComponent(JTree arg0, Object arg1, boolean arg2, boolean arg3, boolean arg4, int arg5) {

        clickedOn =arg1;
        edit=new JTextField(arg1.toString());
        edit.addActionListener(this);
        return edit;
    }



    public boolean isCellEditable(EventObject arg0) {

        if (arg0 instanceof MouseEvent)
            if (((MouseEvent)arg0).getClickCount()==3){
                return true;
            }
        return false;
    }




    public void actionPerformed(ActionEvent e){

        if (!(clickedOn instanceof DraftTreeItem))
            return;

        if(((DraftTreeItem) clickedOn).getDraftNode() instanceof ProjectExplorer){
            return;
        }
        DraftTreeItem clicked = (DraftTreeItem) clickedOn;
        clicked.setName(e.getActionCommand());
        jTreeActions.observerMEthoda(clicked.getDraftNode());
    }
}
