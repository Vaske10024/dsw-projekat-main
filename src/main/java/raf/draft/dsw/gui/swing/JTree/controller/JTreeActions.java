package raf.draft.dsw.gui.swing.JTree.controller;

import raf.draft.dsw.controller.Command.JTreeCommands.AddNodeCommand;
import raf.draft.dsw.controller.messagegenerator.MessageGenerator;
import raf.draft.dsw.gui.swing.JTree.ChildFactory;
import raf.draft.dsw.gui.swing.JTree.Observer.TreeSubject;
import raf.draft.dsw.gui.swing.JTree.model.DraftTreeItem;
import raf.draft.dsw.gui.swing.JTree.view.DraftTreeView;
import raf.draft.dsw.model.Command;
import raf.draft.dsw.model.messages.MessageType;
import raf.draft.dsw.model.nodes.DraftNode;
import raf.draft.dsw.model.nodes.DraftNodeComposite;
import raf.draft.dsw.model.structures.Project;
import raf.draft.dsw.model.structures.ProjectExplorer;
import raf.draft.dsw.model.structures.Room;
import raf.draft.dsw.model.structures.Elementi.RoomElement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.util.ArrayList;

public class JTreeActions {
    private DraftTreeView treeView;
    private DefaultTreeModel treeModel;
    private MessageGenerator messageGenerator;
    private ChildFactory childFactory;
    private TreeSubject treeSubject;

    public JTreeActions(MessageGenerator messageGenerator, DefaultTreeModel treeModel, ProjectExplorer root, TreeSubject treeSubject) {
        this.treeSubject = treeSubject;
        this.messageGenerator = messageGenerator;
        this.treeModel = treeModel;
        this.treeView = generateTree(root);
    }

    public DraftTreeView generateTree(ProjectExplorer projectExplorer) {
        DraftTreeItem root = new DraftTreeItem(projectExplorer);
        treeModel = new DefaultTreeModel(root);
        treeView = new DraftTreeView(treeModel, this);
        setupContextMenu();
        return treeView;
    }

    public void removeNode() {
        DraftTreeItem selectedNode = getSelectedNode();
        DraftNode temp = selectedNode != null ? selectedNode.getDraftNode().getParent() : null;

        if (selectedNode != null) {
            if (selectedNode.getDraftNode() instanceof RoomElement) {
                System.out.println("Koristite DeleteState za Brisanje RoomElementa");
                return;
            }
            if (selectedNode.getDraftNode() instanceof ProjectExplorer) {
                messageGenerator.generateMessage("Cannot remove the root ProjectExplorer node.", MessageType.GRESKA);
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

    public void addChild(DraftTreeItem parent) {
        DraftTreeItem selectedNode = getSelectedNode();
        if (parent == null) {
            messageGenerator.generateMessage("Selektujte Cvor", MessageType.GRESKA);
            return;
        }

        childFactory = new ChildFactory();
        DraftNode child = childFactory.createChild(parent.getDraftNode(), messageGenerator);

        if (child instanceof Project) {
            // Create styled panel for input dialog
            JPanel panel = new JPanel(new BorderLayout(10, 10));
            panel.setBackground(new Color(0xFAFAFA));
            panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

            JLabel label = new JLabel("Unesi autora projekta:");
            label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            label.setForeground(new Color(0x2D2D2D));
            panel.add(label, BorderLayout.NORTH);

            JTextField textField = new JTextField(20);
            textField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            textField.setBackground(new Color(0xFFFFFF));
            textField.setForeground(new Color(0x2D2D2D));
            textField.setBorder(BorderFactory.createLineBorder(new Color(0xE0E0E0), 1));
            panel.add(textField, BorderLayout.CENTER);

            // Set UIManager properties
            UIManager.put("OptionPane.background", new Color(0xFAFAFA));
            UIManager.put("Panel.background", new Color(0xFAFAFA));
            UIManager.put("OptionPane.messageFont", new Font("Segoe UI", Font.PLAIN, 14));
            UIManager.put("OptionPane.buttonFont", new Font("Segoe UI", Font.PLAIN, 13));

            JOptionPane optionPane = new JOptionPane(panel, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
            JDialog dialog = optionPane.createDialog(treeView, "Postavi Autora");
            dialog.getContentPane().setBackground(new Color(0xFAFAFA));

            // Style buttons
            for (Component comp : optionPane.getComponents()) {
                if (comp instanceof JPanel buttonPanel) {
                    for (Component button : ((JPanel) buttonPanel).getComponents()) {
                        if (button instanceof JButton) {
                            styleButton((JButton) button);
                        }
                    }
                }
            }

            dialog.setVisible(true);
            int result = (Integer) optionPane.getValue();
            String autor = textField.getText().trim();
            if (result == JOptionPane.OK_OPTION && !autor.isEmpty()) {
                ((Project) child).setAutor(autor);
            } else {
                ((Project) child).setAutor("NEZNANI AUTOR");
            }
        }

        if (child != null) {
            parent.add(new DraftTreeItem(child));
            ((DraftNodeComposite) parent.getDraftNode()).addChild(child);
            treeView.expandPath(treeView.getSelectionPath());
            SwingUtilities.updateComponentTreeUI(treeView);
        }
        observerMEthoda(selectedNode != null ? selectedNode.getDraftNode() : null);
        if (child != null) {
            Command addNodeCommand = new AddNodeCommand(parent, child, this);
        }
    }

    public void addProjectToTree(Project project) {
        if (project == null) {
            throw new IllegalArgumentException("Project cannot be null.");
        }

        DraftTreeItem rootItem = (DraftTreeItem) treeModel.getRoot();
        if (rootItem == null) {
            throw new IllegalStateException("Tree root is not set.");
        }

        DraftTreeItem projectItem = new DraftTreeItem(project);
        treeModel.insertNodeInto(projectItem, rootItem, rootItem.getChildCount());
        addChildrenRecursivelyToTree(projectItem, project);
        TreePath projectPath = new TreePath(projectItem.getPath());
        treeView.scrollPathToVisible(projectPath);
        treeView.setSelectionPath(projectPath);
    }

    private void addChildrenRecursivelyToTree(DraftTreeItem parentItem, DraftNodeComposite parentNode) {
        for (DraftNode child : parentNode.getChildren()) {
            DraftTreeItem childItem = new DraftTreeItem(child);
            parentItem.add(childItem);
            if (child instanceof DraftNodeComposite) {
                addChildrenRecursivelyToTree(childItem, (DraftNodeComposite) child);
            }
        }
        treeModel.reload(parentItem);
    }

    public void addRoomElementToTree(Room room, RoomElement roomElement) {
        DraftTreeItem roomNode = findNodeForRoom(room);
        if (roomNode == null) {
            messageGenerator.generateMessage("Soba nije pronađena u stablu.", MessageType.GRESKA);
            return;
        }
        roomElement.setParent(room);
        DraftTreeItem roomElementNode = new DraftTreeItem(roomElement);
        roomNode.add(roomElementNode);
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
        DraftTreeItem roomNode = findNodeForRoom(room);
        if (roomNode == null) {
            messageGenerator.generateMessage("Soba nije pronađena u stablu.", MessageType.GRESKA);
            return;
        }
        DraftTreeItem roomElementNode = findNodeForRoomElement(roomNode, roomElement);
        if (roomElementNode == null) {
            messageGenerator.generateMessage("Element nije pronađen u stablu.", MessageType.GRESKA);
            return;
        }
        ((DraftNodeComposite) roomNode.getDraftNode()).removeChild(roomElement);
        roomNode.remove(roomElementNode);
        treeModel.reload(roomNode);
        SwingUtilities.updateComponentTreeUI(treeView);
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

    public void addNodeToTree(DraftNode child, DraftNode parent) {
        if (child == null || parent == null) {
            throw new IllegalArgumentException("Child and parent nodes cannot be null.");
        }
        DraftTreeItem parentNode = findNodeRecursive((DraftTreeItem) treeModel.getRoot(), parent);
        if (parentNode == null) {
            messageGenerator.generateMessage("Parent node not found in the tree.", MessageType.GRESKA);
            return;
        }
        DraftTreeItem childNode = new DraftTreeItem(child);
        if (parent instanceof DraftNodeComposite) {
            ((DraftNodeComposite) parent).addChild(child);
        } else {
            messageGenerator.generateMessage("Parent node must be a composite node.", MessageType.GRESKA);
            return;
        }
        parentNode.add(childNode);
        treeModel.reload(parentNode);
        SwingUtilities.updateComponentTreeUI(treeView);
        treeView.expandPath(new TreePath(parentNode.getPath()));
        observerMEthoda(child);
    }

    public void renameNode(DraftTreeItem selectedNode) {
        if (selectedNode == null) {
            messageGenerator.generateMessage("No node selected for renaming.", MessageType.GRESKA);
            return;
        }

        // Create styled panel for input dialog
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(0xFAFAFA));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel label = new JLabel("Enter new name:");
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        label.setForeground(new Color(0x2D2D2D));
        panel.add(label, BorderLayout.NORTH);

        JTextField textField = new JTextField(20);
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        textField.setBackground(new Color(0xFFFFFF));
        textField.setForeground(new Color(0x2D2D2D));
        textField.setBorder(BorderFactory.createLineBorder(new Color(0xE0E0E0), 1));
        panel.add(textField, BorderLayout.CENTER);

        // Set UIManager properties
        UIManager.put("OptionPane.background", new Color(0xFAFAFA));
        UIManager.put("Panel.background", new Color(0xFAFAFA));
        UIManager.put("OptionPane.messageFont", new Font("Segoe UI", Font.PLAIN, 14));
        UIManager.put("OptionPane.buttonFont", new Font("Segoe UI", Font.PLAIN, 13));

        JOptionPane optionPane = new JOptionPane(panel, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
        JDialog dialog = optionPane.createDialog(treeView, "Rename Node");
        dialog.getContentPane().setBackground(new Color(0xFAFAFA));

        // Style buttons
        for (Component comp : optionPane.getComponents()) {
            if (comp instanceof JPanel buttonPanel) {
                for (Component button : ((JPanel) buttonPanel).getComponents()) {
                    if (button instanceof JButton) {
                        styleButton((JButton) button);
                    }
                }
            }
        }

        dialog.setVisible(true);
        int result = (Integer) optionPane.getValue();
        String newName = textField.getText().trim();

        ArrayList<String> imenaSvihNodeovaOdTihParenta = new ArrayList<>();
        DraftNodeComposite node2 = (DraftNodeComposite) selectedNode.getDraftNode().getParent();

        if (node2 != null) {
            for (DraftNode node : node2.getChildren()) {
                imenaSvihNodeovaOdTihParenta.add(node.getNaziv());
            }
        }

        if (imenaSvihNodeovaOdTihParenta.contains(newName)) {
            messageGenerator.generateMessage("Vec postoji to ime", MessageType.GRESKA);
            return;
        }

        if (result == JOptionPane.OK_OPTION && !newName.isEmpty()) {
            selectedNode.getDraftNode().setNaziv(newName);
            treeModel.nodeChanged(selectedNode);
            SwingUtilities.updateComponentTreeUI(treeView);
        } else {
            messageGenerator.generateMessage("Invalid name. Rename operation canceled.", MessageType.GRESKA);
        }
        observerMEthoda(selectedNode.getDraftNode());
    }

    public void promeniPutanju(DraftTreeItem selectedNode) {
        if (selectedNode == null) {
            messageGenerator.generateMessage("No node selected for renaming.", MessageType.GRESKA);
            return;
        }

        // Create styled panel for input dialog
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(0xFAFAFA));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel label = new JLabel("Unesi Novu putanju:");
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        label.setForeground(new Color(0x2D2D2D));
        panel.add(label, BorderLayout.NORTH);

        JTextField textField = new JTextField(20);
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        textField.setBackground(new Color(0xFFFFFF));
        textField.setForeground(new Color(0x2D2D2D));
        textField.setBorder(BorderFactory.createLineBorder(new Color(0xE0E0E0), 1));
        panel.add(textField, BorderLayout.CENTER);

        // Set UIManager properties
        UIManager.put("OptionPane.background", new Color(0xFAFAFA));
        UIManager.put("Panel.background", new Color(0xFAFAFA));
        UIManager.put("OptionPane.messageFont", new Font("Segoe UI", Font.PLAIN, 14));
        UIManager.put("OptionPane.buttonFont", new Font("Segoe UI", Font.PLAIN, 13));

        JOptionPane optionPane = new JOptionPane(panel, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
        JDialog dialog = optionPane.createDialog(treeView, "Promeni putanju");
        dialog.getContentPane().setBackground(new Color(0xFAFAFA));

        // Style buttons
        for (Component comp : optionPane.getComponents()) {
            if (comp instanceof JPanel buttonPanel) {
                for (Component button : ((JPanel) buttonPanel).getComponents()) {
                    if (button instanceof JButton) {
                        styleButton((JButton) button);
                    }
                }
            }
        }

        dialog.setVisible(true);
        int result = (Integer) optionPane.getValue();
        String novaPutanja = textField.getText().trim();

        if (result == JOptionPane.OK_OPTION && !novaPutanja.isEmpty()) {
            selectedNode.getDraftNode().setPutanjaDoProjekta(novaPutanja);
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
        if (!(selectedNode.getDraftNode() instanceof Project)) {
            messageGenerator.generateMessage("Mozes samo projekat da reautorujes", MessageType.GRESKA);
            return;
        }

        // Create styled panel for input dialog
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(0xFAFAFA));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel label = new JLabel("Unesi novog autora:");
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        label.setForeground(new Color(0x2D2D2D));
        panel.add(label, BorderLayout.NORTH);

        JTextField textField = new JTextField(20);
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        textField.setBackground(new Color(0xFFFFFF));
        textField.setForeground(new Color(0x2D2D2D));
        textField.setBorder(BorderFactory.createLineBorder(new Color(0xE0E0E0), 1));
        panel.add(textField, BorderLayout.CENTER);

        // Set UIManager properties
        UIManager.put("OptionPane.background", new Color(0xFAFAFA));
        UIManager.put("Panel.background", new Color(0xFAFAFA));
        UIManager.put("OptionPane.messageFont", new Font("Segoe UI", Font.PLAIN, 14));
        UIManager.put("OptionPane.buttonFont", new Font("Segoe UI", Font.PLAIN, 13));

        JOptionPane optionPane = new JOptionPane(panel, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
        JDialog dialog = optionPane.createDialog(treeView, "Promeni Autora");
        dialog.getContentPane().setBackground(new Color(0xFAFAFA));

        // Style buttons
        for (Component comp : optionPane.getComponents()) {
            if (comp instanceof JPanel buttonPanel) {
                for (Component button : ((JPanel) buttonPanel).getComponents()) {
                    if (button instanceof JButton) {
                        styleButton((JButton) button);
                    }
                }
            }
        }

        dialog.setVisible(true);
        int result = (Integer) optionPane.getValue();
        String autor = textField.getText().trim();

        if (result == JOptionPane.OK_OPTION && !autor.isEmpty()) {
            ((Project) selectedNode.getDraftNode()).setAutor(autor);
            treeModel.nodeChanged(selectedNode);
            SwingUtilities.updateComponentTreeUI(treeView);
        } else {
            messageGenerator.generateMessage("Invalid name. Reautoring operation canceled.", MessageType.GRESKA);
        }
        observerMEthoda(selectedNode.getDraftNode());
    }

    private void setupContextMenu() {
        JPopupMenu contextMenu = new JPopupMenu();
        contextMenu.setBackground(new Color(0xFAFAFA));
        contextMenu.setBorder(BorderFactory.createLineBorder(new Color(0xE0E0E0), 1));

        // Add menu items
        JMenuItem addChildItem = new JMenuItem("Add Child");
        styleMenuItem(addChildItem);
        addChildItem.addActionListener(e -> addChild(getSelectedNode()));
        contextMenu.add(addChildItem);

        JMenuItem renameItem = new JMenuItem("Rename");
        styleMenuItem(renameItem);
        renameItem.addActionListener(e -> renameNode(getSelectedNode()));
        contextMenu.add(renameItem);

        JMenuItem removeItem = new JMenuItem("Remove");
        styleMenuItem(removeItem);
        removeItem.addActionListener(e -> removeNode());
        contextMenu.add(removeItem);

        JMenuItem promeniPutanju = new JMenuItem("Promena Putanje");
        styleMenuItem(promeniPutanju);
        promeniPutanju.addActionListener(e -> promeniPutanju(getSelectedNode()));
        contextMenu.add(promeniPutanju);

        JMenuItem setAutor = new JMenuItem("Set Autor");
        styleMenuItem(setAutor);
        setAutor.addActionListener(e -> setAutor(getSelectedNode()));
        contextMenu.add(setAutor);

        treeView.setComponentPopupMenu(contextMenu);
    }

    private void styleMenuItem(JMenuItem menuItem) {
        menuItem.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        menuItem.setBackground(new Color(0xFAFAFA));
        menuItem.setForeground(new Color(0x2D2D2D));
        menuItem.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        menuItem.setOpaque(true);

        menuItem.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                menuItem.setBackground(new Color(0xE8ECEF));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                menuItem.setBackground(new Color(0xFAFAFA));
            }

            @Override
            public void mousePressed(MouseEvent e) {
                menuItem.setBackground(new Color(0xB0B0B0));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                menuItem.setBackground(menuItem.getModel().isRollover() ? new Color(0xE8ECEF) : new Color(0xFAFAFA));
            }
        });
    }

    private void styleButton(JButton button) {
        button.setOpaque(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setForeground(new Color(0x2D2D2D));
        button.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(new Color(0xE8ECEF));
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                button.setBackground(null);
            }

            @Override
            public void mousePressed(MouseEvent evt) {
                button.setBackground(new Color(0xB0B0B0));
            }

            @Override
            public void mouseReleased(MouseEvent evt) {
                button.setBackground(button.getModel().isRollover() ? new Color(0xE8ECEF) : null);
            }
        });

        button.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override
            protected void paintButtonPressed(Graphics g, AbstractButton b) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(0xB0B0B0));
                g2d.fill(new RoundRectangle2D.Float(0, 0, b.getWidth(), b.getHeight(), 8, 8));
                g2d.dispose();
            }


            protected void paintBackground(Graphics g, AbstractButton b) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (b.getModel().isRollover()) {
                    g2d.setColor(new Color(0xE8ECEF));
                } else {
                    g2d.setColor(new Color(0xFAFAFA));
                }
                g2d.fill(new RoundRectangle2D.Float(0, 0, b.getWidth(), b.getHeight(), 8, 8));
                g2d.dispose();
            }
        });
    }

    public void observerMEthoda(DraftNode selectedNode) {
        Project project = null;
        if (selectedNode == null) {
            return;
        }
        if (selectedNode instanceof ProjectExplorer) {
            // No action for ProjectExplorer
        } else if (selectedNode instanceof Project) {
            project = (Project) selectedNode;
            treeSubject.notifyObservers(project);
        } else {
            if (selectedNode.getParent() == null) {
                return;
            }
            if (selectedNode.getParent() instanceof Project) {
                project = (Project) selectedNode.getParent();
            } else if (selectedNode.getParent().getParent() != null && selectedNode.getParent().getParent() instanceof Project) {
                project = (Project) selectedNode.getParent().getParent();
            }
            treeSubject.notifyObservers(project);
        }
    }

    public void observerMEthodaV2(DraftNode selectedNode) {
        Project project = null;
        if (selectedNode == null) {
            return;
        }
        if (selectedNode instanceof ProjectExplorer) {
            treeSubject.notifyObservers(project);
        } else if (selectedNode instanceof Project) {
            project = (Project) selectedNode;
            treeSubject.notifyObservers(project);
        } else {
            if (selectedNode.getParent() == null) {
                return;
            }
            if (selectedNode.getParent() instanceof Project) {
                project = (Project) selectedNode.getParent();
            }
            treeSubject.notifyObservers(project);
        }
    }

    public DraftTreeItem getSelectedNode() {
        return (DraftTreeItem) treeView.getLastSelectedPathComponent();
    }

    public DraftTreeView getTreeView() {
        return treeView;
    }
}