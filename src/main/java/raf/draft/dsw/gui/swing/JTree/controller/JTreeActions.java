    package raf.draft.dsw.gui.swing.JTree.controller;
    
    import raf.draft.dsw.controller.Command.JTreeCommands.AddNodeCommand;
    import raf.draft.dsw.controller.messagegenerator.MessageGenerator;
    import raf.draft.dsw.gui.swing.JTree.ChildFactory;
    import raf.draft.dsw.gui.swing.JTree.Observer.TreeSubject;
    import raf.draft.dsw.gui.swing.JTree.model.DraftTreeItem;
    import raf.draft.dsw.gui.swing.JTree.view.DraftTreeView;
    import raf.draft.dsw.gui.swing.JTabbedPaneClass;
    import raf.draft.dsw.model.Command;
    import raf.draft.dsw.model.messages.MessageType;
    import raf.draft.dsw.model.nodes.DraftNode;
    import raf.draft.dsw.model.nodes.DraftNodeComposite;
    import raf.draft.dsw.model.structures.Building;
    import raf.draft.dsw.model.structures.Elementi.RoomElement;
    import raf.draft.dsw.model.structures.Project;
    import raf.draft.dsw.model.structures.ProjectExplorer;
    import raf.draft.dsw.model.structures.Room;
    
    import javax.swing.*;
    import javax.swing.tree.DefaultTreeModel;
    import javax.swing.tree.TreePath;
    import java.lang.reflect.Array;
    import java.util.ArrayList;
    
    public class JTreeActions {
        private DraftTreeView treeView;
    
        private DefaultTreeModel treeModel;
    
        private MessageGenerator messageGenerator;
    
        private ChildFactory childFactory;
    
        private TreeSubject treeSubject;
    
    
        public JTreeActions(MessageGenerator messageGenerator, DefaultTreeModel treeModel, ProjectExplorer root,TreeSubject treeSubject) {
            this.treeSubject= treeSubject;
            this.messageGenerator=messageGenerator;
            this.treeModel=treeModel;
            this.treeView=generateTree(root);
        }
    
    
        public DraftTreeView generateTree(ProjectExplorer projectExplorer){
            DraftTreeItem root = new DraftTreeItem(projectExplorer);
            treeModel = new DefaultTreeModel(root);
            treeView = new DraftTreeView(treeModel,this);
            setupContextMenu();
            return treeView;
        }
    
    
    
        public void removeNode() {
            DraftTreeItem selectedNode = getSelectedNode();
    
            DraftNode temp = selectedNode.getDraftNode().getParent();
    
            if (selectedNode != null) {
                if(selectedNode.getDraftNode() instanceof RoomElement){
                    System.out.println("Koristite DeleteState za Brisanje RoomElementa");
                    return;
                }
                if (selectedNode.getDraftNode() instanceof ProjectExplorer) {
    
                    messageGenerator.generateMessage("Cannot remove the root ProjectExplorer node.",MessageType.GRESKA);
    
                } else {
    
                    DraftTreeItem parentNode = (DraftTreeItem) selectedNode.getParent();
                    DraftNodeComposite parentDraftNode = (DraftNodeComposite) parentNode.getDraftNode();
    
                    if (parentDraftNode != null) {
                        parentDraftNode.removeChild(selectedNode.getDraftNode());
                        parentNode.remove(selectedNode);
                        treeModel.reload(parentNode);
                        SwingUtilities.updateComponentTreeUI(treeView);
                    }
                }
            } else {
                messageGenerator.generateMessage("No node selected for removal.", MessageType.GRESKA);
            }
    
            observerMEthodaV2(temp);
        }
    
        public void addChild(DraftTreeItem parent){
            DraftTreeItem selectedNode = getSelectedNode();
    
            ///Proveravamo da nije slucajno soba
            if(parent==null){
                messageGenerator.generateMessage("Selektujte Cvor", MessageType.GRESKA);
                return;
            }
    
    
    
            childFactory=new ChildFactory();
            DraftNode child = childFactory.createChild(parent.getDraftNode(),messageGenerator);
    
            if(child instanceof Project){
                String autor = JOptionPane.showInputDialog(
                        treeView,
                        "Unesi autora projekta:",
                        "Postavi Autora",
                        JOptionPane.PLAIN_MESSAGE
                );
                if(autor==null || autor.equals("")){
                    autor = "NEZNANI AUTOR";
                }
                ((Project) child).setAutor(autor);
            }
    
            if(child==null){
            }else{
                parent.add(new DraftTreeItem(child));
                ((DraftNodeComposite) parent.getDraftNode()).addChild(child);
                treeView.expandPath(treeView.getSelectionPath());
                SwingUtilities.updateComponentTreeUI(treeView);
            }
            observerMEthoda(selectedNode.getDraftNode());
            Command addNodeCommand = new AddNodeCommand(parent, child, this);
            
        }
    
        public void addProjectToTree(Project project) {
            if (project == null) {
                throw new IllegalArgumentException("Project cannot be null.");
            }
    
            DraftTreeItem rootItem = (DraftTreeItem) treeModel.getRoot();
            if (rootItem == null) {
                throw new IllegalStateException("Tree root is not set.");
            }
    
            // Create a new tree node for the project
            DraftTreeItem projectItem = new DraftTreeItem(project);
    
            // Add the project node to the root
            treeModel.insertNodeInto(projectItem, rootItem, rootItem.getChildCount());
    
            // Recursively add children of the project to the tree
            addChildrenRecursivelyToTree(projectItem, project);
    
            // Refresh the tree display and focus on the new project node
            TreePath projectPath = new TreePath(projectItem.getPath());
            treeView.scrollPathToVisible(projectPath);
            treeView.setSelectionPath(projectPath);
        }
    
        private void addChildrenRecursivelyToTree(DraftTreeItem parentItem, DraftNodeComposite parentNode) {
            for (DraftNode child : parentNode.getChildren()) {
                // Create a tree node for the child
                DraftTreeItem childItem = new DraftTreeItem(child);
    
                // Add the child node to the parent node in the tree
                parentItem.add(childItem);
    
                // If the child is a composite node, recursively add its children
                if (child instanceof DraftNodeComposite) {
                    addChildrenRecursivelyToTree(childItem, (DraftNodeComposite) child);
                }
            }
    
            // Update the tree model to reflect changes
            treeModel.reload(parentItem);
        }
    
    
        public void addRoomElementToTree(Room room, RoomElement roomElement) {
            // Pronađi čvor sobe u JTree
            DraftTreeItem roomNode = findNodeForRoom(room);
            if (roomNode == null) {
                messageGenerator.generateMessage("Soba nije pronađena u stablu.", MessageType.GRESKA);
                return;
            }
            roomElement.setParent(room);
            // Kreiraj čvor za RoomElement
            DraftTreeItem roomElementNode = new DraftTreeItem(roomElement);
    
            // Dodaj RoomElement kao child
            roomNode.add(roomElementNode);
    
            // Ažuriraj prikaz stabla
            treeModel.reload(roomNode);
            SwingUtilities.updateComponentTreeUI(treeView);
        }
        private DraftTreeItem findNodeForRoom(Room room) {
            DraftTreeItem root = (DraftTreeItem) treeModel.getRoot();
            return findNodeRecursive(root, room);
        }
    
        private DraftTreeItem findNodeRecursive(DraftTreeItem currentNode, DraftNode targetNode) {
            if (currentNode.getDraftNode().equals(targetNode)) {
                return currentNode;
            }
            for (int i = 0; i < currentNode.getChildCount(); i++) {
                DraftTreeItem childNode = (DraftTreeItem) currentNode.getChildAt(i);
                DraftTreeItem foundNode = findNodeRecursive(childNode, targetNode);
                if (foundNode != null) {
                    return foundNode;
                }
            }
            return null;
        }
        public void removeRoomElementFromTree(Room room, RoomElement roomElement) {
            // Pronađi čvor sobe u JTree
            DraftTreeItem roomNode = findNodeForRoom(room);
            if (roomNode == null) {
                messageGenerator.generateMessage("Soba nije pronađena u stablu.", MessageType.GRESKA);
                return;
            }
    
            // Pronađi čvor RoomElement-a unutar sobe
            DraftTreeItem roomElementNode = findNodeForRoomElement(roomNode, roomElement);
            if (roomElementNode == null) {
                messageGenerator.generateMessage("Element nije pronađen u stablu.", MessageType.GRESKA);
                return;
            }
    
            // Ukloni RoomElement kao dete iz čvora sobe
            ((DraftNodeComposite) roomNode.getDraftNode()).removeChild(roomElement);
            roomNode.remove(roomElementNode);
    
            // Ažuriraj prikaz stabla
            treeModel.reload(roomNode);
            SwingUtilities.updateComponentTreeUI(treeView);
        }
        public void addNodeToTree(DraftNode child, DraftNode parent) {
            if (child == null || parent == null) {
                throw new IllegalArgumentException("Child and parent nodes cannot be null.");
            }
    
            // Find the parent node in the JTree
            DraftTreeItem parentNode = findNodeRecursive((DraftTreeItem) treeModel.getRoot(), parent);
            if (parentNode == null) {
                messageGenerator.generateMessage("Parent node not found in the tree.", MessageType.GRESKA);
                return;
            }
    
            // Create a new tree item for the child
            DraftTreeItem childNode = new DraftTreeItem(child);
    
            // Add the child to the parent's children list
            if (parent instanceof DraftNodeComposite) {
                ((DraftNodeComposite) parent).addChild(child);
            } else {
                messageGenerator.generateMessage("Parent node must be a composite node.", MessageType.GRESKA);
                return;
            }
    
            // Add the child node to the parent node in the tree
            parentNode.add(childNode);
    
            // Update the tree model and UI
            treeModel.reload(parentNode);
            SwingUtilities.updateComponentTreeUI(treeView);
    
            // Expand the parent node to make the child node visible
            treeView.expandPath(new TreePath(parentNode.getPath()));
    
            // Notify observers if needed
            observerMEthoda(child);
        }
        private DraftTreeItem findNodeForRoomElement(DraftTreeItem roomNode, RoomElement roomElement) {
            for (int i = 0; i < roomNode.getChildCount(); i++) {
                DraftTreeItem childNode = (DraftTreeItem) roomNode.getChildAt(i);
                if (childNode.getDraftNode().equals(roomElement)) {
                    return childNode;
                }
            }
            return null;
        }
    
    
        public void renameNode(DraftTreeItem selectedNode) {
            if (selectedNode == null) {
                messageGenerator.generateMessage("No node selected for renaming.",MessageType.GRESKA);
                return;
            }
    
    
            String newName = JOptionPane.showInputDialog(
                    treeView,
                    "Enter new name:",
                    "Rename Node",
                    JOptionPane.PLAIN_MESSAGE
            );
            ArrayList<String> imenaSvihNodeovaOdTihParenta= new ArrayList<>();
    
            DraftNodeComposite node2= (DraftNodeComposite) selectedNode.getDraftNode().getParent();
    
            for(DraftNode node: node2.getChildren()){
                imenaSvihNodeovaOdTihParenta.add(node.getNaziv());
            }
    
            if(imenaSvihNodeovaOdTihParenta.contains(newName)){
                messageGenerator.generateMessage("Vec postoji to ime",MessageType.GRESKA);
                return;
            }
    
            if (newName != null && !newName.trim().isEmpty()) {
                selectedNode.getDraftNode().setNaziv(newName);
                treeModel.nodeChanged(selectedNode);
                SwingUtilities.updateComponentTreeUI(treeView);
            } else {
                messageGenerator.generateMessage("Invalid name. Rename operation canceled.",MessageType.GRESKA);
            }
            observerMEthoda(selectedNode.getDraftNode());
        }
    
        public void observerMEthoda(DraftNode selectedNode){
            Project project= null;
    
            if(selectedNode==null){
                return;
            }
            if( selectedNode instanceof ProjectExplorer){
    
            }
            else if(selectedNode instanceof Project){
                project= (Project) selectedNode;
                treeSubject.notifyObservers(project);
            }
            else{
                if(selectedNode.getParent()==null){
                    return;
                }
                if(selectedNode.getParent() instanceof Project){
                    project= (Project) selectedNode.getParent();
                }
                else if(selectedNode.getParent().getParent()==null){
    
                }
                else if(selectedNode.getParent().getParent() instanceof Project){
                    project= (Project) selectedNode.getParent().getParent();
                }
                treeSubject.notifyObservers(project);
            }
        }
    
    
        //Observer metodu dva koristimo kod brisanja nodeova Zbog malih komplikacija
        public void observerMEthodaV2(DraftNode selectedNode){
            Project project= null;
    
            if(selectedNode==null){
                return;
            }
            if( selectedNode instanceof ProjectExplorer){
                treeSubject.notifyObservers(project);
            }
            else if(selectedNode instanceof Project){
                project= (Project) selectedNode;
                treeSubject.notifyObservers(project);
            }
            else{
                if(selectedNode.getParent()==null){
                    return;
                }
                if(selectedNode.getParent() instanceof Project){
                    project= (Project) selectedNode.getParent();
                }
                else if(selectedNode.getParent().getParent()==null){
    
                }
                treeSubject.notifyObservers(project);
            }
        }
    
    
    
        public void promeniPutanju(DraftTreeItem selectedNode) {
            if (selectedNode == null) {
                messageGenerator.generateMessage("No node selected for renaming.", MessageType.GRESKA);
                return;
            }
    
            String novaPutanja = JOptionPane.showInputDialog(
                    treeView,
                    "Unesi Novu putanju:",
                    "Promeni putanju",
                    JOptionPane.PLAIN_MESSAGE
            );
    
            if (novaPutanja != null && !novaPutanja.trim().isEmpty()) {
                selectedNode.getDraftNode().setPutanjaDoProjekta(novaPutanja.trim());
    
    
                treeModel.nodeChanged(selectedNode);
                SwingUtilities.updateComponentTreeUI(treeView);
            } else {
                messageGenerator.generateMessage("Invalid name. Rename operation canceled.", MessageType.GRESKA);
            }
    
            observerMEthoda(selectedNode.getDraftNode());
        }
    
        public void setAutor(DraftTreeItem selectedNode) {
            if (selectedNode == null) {
                messageGenerator.generateMessage("No node selected for reAutoring.", MessageType.GRESKA);
                return;
            }
    
            if(!(selectedNode.getDraftNode() instanceof Project)){
                messageGenerator.generateMessage("Mozes samo projekat da reautorujes",MessageType.GRESKA);
                return;
            }
    
    
            String autor = JOptionPane.showInputDialog(
                    treeView,
                    "Unesi novog autora:",
                    "Promeni Autora",
                    JOptionPane.PLAIN_MESSAGE
            );
    
            if (autor != null && !autor.trim().isEmpty()) {
    
                ((Project) selectedNode.getDraftNode()).setAutor(autor.trim());
    
                treeModel.nodeChanged(selectedNode);
                SwingUtilities.updateComponentTreeUI(treeView);
            } else {
                messageGenerator.generateMessage("Invalid name. Reautoring operation canceled.", MessageType.GRESKA);
            }
            observerMEthoda(selectedNode.getDraftNode());
    
        }
    
    
    
    
    
        private void setupContextMenu() {
            JPopupMenu contextMenu = new JPopupMenu();
    
            JMenuItem addChildItem = new JMenuItem("Add Child");
            addChildItem.addActionListener(e -> addChild(getSelectedNode()));
            contextMenu.add(addChildItem);
    
            JMenuItem renameItem = new JMenuItem("Rename");
            renameItem.addActionListener(e -> renameNode(getSelectedNode()));
            contextMenu.add(renameItem);
    
    
            JMenuItem removeItem = new JMenuItem("Remove");
            removeItem.addActionListener(e -> removeNode());
            contextMenu.add(removeItem);
    
            JMenuItem promeniPutanju = new JMenuItem("Promena Putanje");
            promeniPutanju.addActionListener(e -> promeniPutanju(getSelectedNode()));
            contextMenu.add(promeniPutanju);
    
            JMenuItem setAutor= new JMenuItem("Set Autor");
            setAutor.addActionListener(e -> setAutor(getSelectedNode()));
            contextMenu.add(setAutor);
    
    
            treeView.setComponentPopupMenu(contextMenu);
    
        }
    
        public DraftTreeItem getSelectedNode(){
            return (DraftTreeItem) treeView.getLastSelectedPathComponent();
        }
    
        public DraftTreeView getTreeView() {
            return treeView;
        }
    }
