    package raf.draft.dsw.gui.swing;

    import raf.draft.dsw.controller.Command.CommandHistory;
    import raf.draft.dsw.controller.UndoRedoListener;
    import raf.draft.dsw.controller.messagegenerator.MessageGenerator;
    import raf.draft.dsw.controller.observer.ISubscriber;
    import raf.draft.dsw.gui.swing.JTree.DraftTreeImplementation;
    import raf.draft.dsw.gui.swing.JTree.Observer.TreeSubject;
    import raf.draft.dsw.gui.swing.state.Mediator;
    import raf.draft.dsw.model.messages.Message;
    import raf.draft.dsw.model.messages.MessageType;
    import raf.draft.dsw.model.structures.Project;
    import raf.draft.dsw.model.structures.ProjectExplorer;

    import javax.swing.*;
    import java.awt.*;
    import java.awt.event.ActionListener;

    public class MainFrame extends JFrame implements ISubscriber,UndoRedoListener {

        private static MainFrame uniqueInstance;
        private ActionMenagerView actionMenagerView = new ActionMenagerView();
        private DraftTreeImplementation draftTreeImplementation;
        private JTabbedPaneClass JTabbedPaneClass;
        private Mediator mediator = new Mediator();
        private TreeSubject treeSubject;
        private CommandHistory commandHistory;
        private JButton undoButton;
        private JButton redoButton;
        private Project project;
        private CommandHistory globalCommandHistory;

        private MainFrame() {
            initialize();
        }

        private void initialize() {
            commandHistory = new CommandHistory();
            JTabbedPaneClass = new JTabbedPaneClass(mediator);
            ActionMenagerState actionMenagerState = new ActionMenagerState();
            MessageGenerator messageGenerator = new MessageGenerator();
            messageGenerator.addSubscriber(this);
            ProjectExplorer root = new ProjectExplorer("Project Explorer");
            draftTreeImplementation = new DraftTreeImplementation(messageGenerator, JTabbedPaneClass, root);

            JTree projectExplorerTree = draftTreeImplementation.getTreeView();

            setSize(1280, 720);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setTitle("DraftRoom");

            MyMenuBar menu = new MyMenuBar();
            setJMenuBar(menu);

            MyToolBar toolBar = new MyToolBar(actionMenagerView);
            add(toolBar, BorderLayout.NORTH);

            MyStateToolBar stateToolBar = new MyStateToolBar(actionMenagerState);

            JScrollPane scroll = new JScrollPane(projectExplorerTree);
            scroll.setMinimumSize(new Dimension(200, 150));
            scroll.setBackground(new Color(0xF5F6F5)); // Match background

            JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scroll, JTabbedPaneClass);
            split.setDividerLocation(250);
            split.setOneTouchExpandable(true);
            split.setBackground(new Color(0xF5F6F5));
            getContentPane().add(split, BorderLayout.CENTER);

            JPanel rightPanel = new JPanel();
            rightPanel.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = 1.0;
            gbc.weighty = 1.0;
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.fill = GridBagConstraints.NONE;
            rightPanel.add(stateToolBar, gbc);
            rightPanel.setBackground(new Color(0xF5F6F5));
            getContentPane().add(rightPanel, BorderLayout.EAST);

            undoButton = new JButton("Undo");
            redoButton = new JButton("Redo");
            styleButton(undoButton);
            styleButton(redoButton);
            setupUndoRedoButtons();
            toolBar.add(undoButton);
            toolBar.add(redoButton);
        }

        private void styleButton(JButton button) {
            button.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            button.setBackground(new Color(0xF5F6F5));
            button.setForeground(new Color(0x333333));
            button.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
            button.setFocusPainted(false);
            button.setContentAreaFilled(false);
            button.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    button.setBackground(new Color(0xE0E0E0));
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    button.setBackground(new Color(0xF5F6F5));
                }
            });
        }

        public static MainFrame getUniqueInstance() {
            if (uniqueInstance == null) {
                uniqueInstance = new MainFrame();
            }
            return uniqueInstance;
        }

        @Override
        public void update(Message message) {
            if (message.getType().equals(MessageType.GRESKA)) {
                JOptionPane.showMessageDialog(this, message.getContent(), "ERROR", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, message.getContent(), "INFO", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        public void setupUndoRedoButtons() {
            undoButton.addActionListener(e -> {
                if (mediator.getRoomView() != null) {
                    mediator.getRoomView().getCommandHistory().undo(); // Undo za trenutnu sobu
                }
            });
            redoButton.addActionListener(e -> {
                if (mediator.getRoomView() != null) {
                    mediator.getRoomView().getCommandHistory().redo(); // Redo za trenutnu sobu
                }
            });
            updateUndoRedo(false,false);
        }
        @Override public void updateUndoRedo(boolean canUndo, boolean canRedo) { undoButton.setEnabled(canUndo); redoButton.setEnabled(canRedo); }

        public DraftTreeImplementation getDraftTreeImplementation() {
            return draftTreeImplementation;
        }

        public Mediator getMediator() {
            return mediator;
        }

        public void setMediator(Mediator mediator) {
            this.mediator = mediator;
        }

        public raf.draft.dsw.gui.swing.JTabbedPaneClass getJTabbedPaneClass() {
            return JTabbedPaneClass;
        }

        public void setJTabbedPaneClass(raf.draft.dsw.gui.swing.JTabbedPaneClass JTabbedPaneClass) {
            this.JTabbedPaneClass = JTabbedPaneClass;
        }

        public CommandHistory getGlobalCommandHistory() {
            return globalCommandHistory;
        }

        public CommandHistory getCommandHistory() {
            return commandHistory;
        }

        public void setCommandHistory(CommandHistory commandHistory) {
            this.commandHistory = commandHistory;
        }

        public Project getProject() {
            return project;
        }

        public void setProject(Project project) {
            this.project = project;
        }
    }
