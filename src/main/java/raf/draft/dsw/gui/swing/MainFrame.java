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
            commandHistory= new CommandHistory();
            JTabbedPaneClass = new JTabbedPaneClass(mediator);
            ActionMenagerState actionMenagerState = new ActionMenagerState();
            MessageGenerator messageGenerator = new MessageGenerator();
            messageGenerator.addSubscriber(this);
            ProjectExplorer root = new ProjectExplorer("Project Explorer");
            draftTreeImplementation = new DraftTreeImplementation(messageGenerator, JTabbedPaneClass, root);

            JTree projectExplorerTree = draftTreeImplementation.getTreeView();

            Toolkit kit = Toolkit.getDefaultToolkit();
            Dimension screenSize = kit.getScreenSize();
            int screenHeight = screenSize.height;
            int screenWidth = screenSize.width;
            setSize(screenWidth / 2, screenHeight / 2);
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

            JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scroll, JTabbedPaneClass);
            getContentPane().add(split, BorderLayout.CENTER);

            // Kreiraj panel za centriranje stateToolBar-a
            JPanel rightPanel = new JPanel();
            rightPanel.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();

            // Postavi centriranje po vertikali
            gbc.gridx = 0; // Kolona 0
            gbc.gridy = 0; // Red 0
            gbc.weightx = 1.0; // Rasporedi horizontalno (ako je potrebno)
            gbc.weighty = 1.0; // Rasporedi vertikalno
            gbc.anchor = GridBagConstraints.CENTER; // Centriranje
            gbc.fill = GridBagConstraints.NONE; // Nemoj širiti komponentu

            rightPanel.add(stateToolBar, gbc);
            rightPanel.setBackground((new Color(0xE6E6F5)));

            // Dodaj desni panel (sa centriranim toolbar-om) na EAST poziciju
            getContentPane().add(rightPanel, BorderLayout.EAST);

            undoButton= new JButton("undo");
            redoButton= new JButton("redo");

             setupUndoRedoButtons();
            toolBar.add(undoButton);
            toolBar.add(redoButton);



            split.setDividerLocation(250);
            split.setOneTouchExpandable(true);

            this.setSize(1280, 720);
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
