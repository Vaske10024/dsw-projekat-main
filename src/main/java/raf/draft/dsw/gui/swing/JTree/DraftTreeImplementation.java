package raf.draft.dsw.gui.swing.JTree;

import raf.draft.dsw.controller.messagegenerator.MessageGenerator;
import raf.draft.dsw.gui.swing.JTree.Observer.TreeSubject;
import raf.draft.dsw.gui.swing.JTree.controller.JTreeActions;
import raf.draft.dsw.gui.swing.JTree.model.DraftTreeItem;
import raf.draft.dsw.gui.swing.JTree.view.DraftTreeView;
import raf.draft.dsw.gui.swing.JTabbedPaneClass;
import raf.draft.dsw.gui.swing.MainFrame;
import raf.draft.dsw.model.nodes.DraftNode;
import raf.draft.dsw.model.structures.Project;
import raf.draft.dsw.model.structures.ProjectExplorer;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DraftTreeImplementation  {

    private static int count=0;
    private DraftTreeView treeView;

    private DefaultTreeModel treeModel;

    private MessageGenerator messageGenerator;

    
    private JTabbedPaneClass JTabbedPaneClass;

    private JTreeActions jTreeActions;

    private TreeSubject treeSubject;
    private DraftTreeItem selectedProjectTreeItem;

    public DraftTreeImplementation(MessageGenerator messageGenerator, JTabbedPaneClass JTabbedPaneClass, ProjectExplorer root) {
        treeSubject= new TreeSubject();
        treeSubject.addObserver(JTabbedPaneClass);
        jTreeActions= new JTreeActions(messageGenerator,treeModel,root,treeSubject);
        this.treeView=jTreeActions.getTreeView();
        this.JTabbedPaneClass = JTabbedPaneClass;
        this.messageGenerator = messageGenerator;
       setupDoubleClickHandler();
    }

    private void setupDoubleClickHandler() {
        treeView.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if (e.getClickCount() == 2) {
                    TreePath path = treeView.getPathForLocation(e.getX(), e.getY());
                    if (path != null) {
                        DraftTreeItem item = (DraftTreeItem) path.getLastPathComponent();
                        DraftNode node = item.getDraftNode();

                        if (node instanceof Project) {
                            Project project = (Project) node;
                            JTabbedPaneClass.displayRooms(project.getAllRooms());
                            treeSubject.notifyObservers(project);
                            selectedProjectTreeItem=item;

                        }
                    }
                }
            }
        });

    }
    public DraftTreeItem getCurrentlySelectedTreeItem() {
        TreePath selectedPath = treeView.getSelectionPath();
        if (selectedPath != null) {
            return (DraftTreeItem) selectedPath.getLastPathComponent();
        }
        return null;
    }


    public DraftTreeView getTreeView() {
        return treeView;
    }

    public void setTreeView(DraftTreeView treeView) {
        this.treeView = treeView;
    }

    public JTreeActions getjTreeActions() {
        return jTreeActions;
    }

    public void setjTreeActions(JTreeActions jTreeActions) {
        this.jTreeActions = jTreeActions;
    }

    public DraftTreeItem getSelectedProjectTreeItem() {
        return selectedProjectTreeItem;
    }

    public void setSelectedProjectTreeItem(DraftTreeItem selectedProjectTreeItem) {
        this.selectedProjectTreeItem = selectedProjectTreeItem;
    }
}
