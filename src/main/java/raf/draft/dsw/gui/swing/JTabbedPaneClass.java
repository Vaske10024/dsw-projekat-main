package raf.draft.dsw.gui.swing;

import com.sun.tools.javac.Main;
import raf.draft.dsw.controller.Command.CommandHistory;
import raf.draft.dsw.gui.swing.JTree.Observer.ITreeObserver;
import raf.draft.dsw.gui.swing.Painter.BojlerPainter;
import raf.draft.dsw.gui.swing.state.Mediator;
import raf.draft.dsw.model.structures.Elementi.Bojler;
import raf.draft.dsw.model.structures.Project;
import raf.draft.dsw.model.structures.Room;

import javax.print.attribute.standard.Media;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class JTabbedPaneClass extends JPanel implements ITreeObserver {
    private JTabbedPane tabbedPane;
    private JLabel projectNameLabel;
    private String label = "Open a Project";
    private Mediator mediator;
    private Project currentProject;

    // Dodata mapa za čuvanje RoomView instanci
    private Map<String, RoomView> roomViewMap = new HashMap<>();

    public JTabbedPaneClass(Mediator mediator) {
        setBackground(new Color(0xe6ecf5));
        this.mediator = mediator;
        setLayout(new BorderLayout());
        projectNameLabel = new JLabel(label);
        projectNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(projectNameLabel, BorderLayout.NORTH);
        tabbedPane = new JTabbedPane();
        add(tabbedPane, BorderLayout.CENTER);

        tabbedPane.addChangeListener(e -> {
            int selectedIndex = tabbedPane.getSelectedIndex();
            if (selectedIndex != -1) {
                JScrollPane scrollPane = (JScrollPane) tabbedPane.getComponentAt(selectedIndex);
                RoomView selectedRoomView = (RoomView) scrollPane.getViewport().getView();
                mediator.setRoomView(selectedRoomView); // Postavljanje trenutnog RoomView-a u mediator
                mediator.getStateMenager().setCurretState(null); // Resetovanje stanja pri promeni sobe
            }
        });
    }

    @Override
    public void updateTabbedPane(Project project) {
        refreshTabs(project);
        MainFrame.getUniqueInstance().setProject(project);
        currentProject = project;
    }

    public void refreshTabs(Project project) {
        if (project == null) {
            projectNameLabel.setText(label);
            displayRooms(null);
            return;
        }

        ArrayList<Room> updatedRooms = project.getAllRooms();
        displayRooms(updatedRooms);

        if (project.getAutor() == null) {
            projectNameLabel.setText(project.getNaziv());
        } else {
            projectNameLabel.setText(project.getNaziv() + " " + project.getAutor());
        }
    }

    public void displayRooms(ArrayList<Room> rooms) {
        tabbedPane.removeAll(); // Remove all tabs

        if (rooms == null) {
            return;
        }

        // Create a new map to hold RoomView instances
        Map<String, RoomView> newRoomViewMap = new HashMap<>();

        for (Room room : rooms) {
            RoomView roomView;

            // Check if RoomView already exists in the map
            if (roomViewMap.containsKey(room.getNaziv())) {
                roomView = roomViewMap.get(room.getNaziv());
            } else {
                roomView = new RoomView(room, mediator);
                roomView.setCommandHistory(new CommandHistory()); // Create a new CommandHistory for this RoomView
            }

            newRoomViewMap.put(room.getNaziv(), roomView);

            String roomName = room.getNaziv();
            if (roomName == null || roomName.isEmpty()) {
                roomName = "Unnamed Room";
            }

            tabbedPane.addTab(roomName, new JScrollPane(roomView)); // Wrap the RoomView in a JScrollPane
            int tabIndex = tabbedPane.indexOfComponent(tabbedPane.getComponentAt(tabbedPane.getTabCount() - 1));
            if (room.getColor() != null) {
                tabbedPane.setBackgroundAt(tabIndex, room.getColor());
            }
        }

        // Update the map
        roomViewMap = newRoomViewMap;

        // Automatically select the first tab if available
        if (tabbedPane.getTabCount() > 0) {
            tabbedPane.setSelectedIndex(0);
            JScrollPane scrollPane = (JScrollPane) tabbedPane.getComponentAt(0);
            RoomView firstRoomView = (RoomView) scrollPane.getViewport().getView();
            mediator.setRoomView(firstRoomView); // Set the first RoomView
            MainFrame.getUniqueInstance().setCommandHistory(firstRoomView.getCommandHistory()); // Set the correct CommandHistory
        }

        tabbedPane.revalidate();
        tabbedPane.repaint();
    }


    public Project getCurrentProject() {
        return currentProject;
    }

    public void setCurrentProject(Project currentProject) {
        this.currentProject = currentProject;
    }

    public JLabel getProjectNameLabel() {
        return projectNameLabel;
    }

    public void setProjectNameLabel(JLabel projectNameLabel) {
        this.projectNameLabel = projectNameLabel;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Room getSelectedRoom() {
        int selectedIndex = tabbedPane.getSelectedIndex();
        if (selectedIndex != -1) {
            JScrollPane scrollPane = (JScrollPane) tabbedPane.getComponentAt(selectedIndex);
            RoomView selectedRoomView = (RoomView) scrollPane.getViewport().getView();
            return selectedRoomView.getRoom();
        }
        return null; // No tab selected or invalid state
    }
}
