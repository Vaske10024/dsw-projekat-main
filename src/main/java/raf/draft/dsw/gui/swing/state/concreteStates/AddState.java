package raf.draft.dsw.gui.swing.state.concreteStates;

import com.sun.tools.javac.Main;
import raf.draft.dsw.controller.Command.AddElementCommand;
import raf.draft.dsw.gui.swing.JTabbedPaneClass;
import raf.draft.dsw.gui.swing.JTree.DraftTreeImplementation;
import raf.draft.dsw.gui.swing.JTree.controller.JTreeActions;
import raf.draft.dsw.gui.swing.JTree.model.DraftTreeItem;
import raf.draft.dsw.gui.swing.MainFrame;
import raf.draft.dsw.gui.swing.Painter.*;
import raf.draft.dsw.gui.swing.PopUpWindows.RoomElementSize;
import raf.draft.dsw.gui.swing.RoomElementFactory.RoomElementFactory;
import raf.draft.dsw.gui.swing.RoomView;
import raf.draft.dsw.gui.swing.state.State;
import raf.draft.dsw.model.nodes.DraftNode;
import raf.draft.dsw.model.structures.Elementi.*;
import raf.draft.dsw.model.structures.Project;
import raf.draft.dsw.model.structures.Room;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.sql.SQLOutput;

import com.sun.tools.javac.Main;
import raf.draft.dsw.gui.swing.JTabbedPaneClass;
import raf.draft.dsw.gui.swing.JTree.DraftTreeImplementation;
import raf.draft.dsw.gui.swing.JTree.controller.JTreeActions;
import raf.draft.dsw.gui.swing.JTree.model.DraftTreeItem;
import raf.draft.dsw.gui.swing.MainFrame;
import raf.draft.dsw.gui.swing.Painter.*;
import raf.draft.dsw.gui.swing.PopUpWindows.RoomElementSize;
import raf.draft.dsw.gui.swing.RoomElementFactory.RoomElementFactory;
import raf.draft.dsw.gui.swing.RoomView;
import raf.draft.dsw.gui.swing.state.State;
import raf.draft.dsw.model.nodes.DraftNode;
import raf.draft.dsw.model.structures.Elementi.*;
import raf.draft.dsw.model.structures.Project;
import raf.draft.dsw.model.structures.Room;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.sql.SQLOutput;

import raf.draft.dsw.controller.Command.AddElementCommand;
import raf.draft.dsw.gui.swing.JTabbedPaneClass;
import raf.draft.dsw.gui.swing.JTree.DraftTreeImplementation;
import raf.draft.dsw.gui.swing.JTree.controller.JTreeActions;
import raf.draft.dsw.gui.swing.MainFrame;
import raf.draft.dsw.gui.swing.Painter.*;
import raf.draft.dsw.gui.swing.PopUpWindows.RoomElementSize;
import raf.draft.dsw.gui.swing.RoomElementFactory.RoomElementFactory;
import raf.draft.dsw.gui.swing.RoomView;
import raf.draft.dsw.gui.swing.state.State;
import raf.draft.dsw.model.structures.Elementi.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public class AddState implements State {

    private RoomElementFactory roomElementFactory;

    private JTreeActions jTreeActions;

    private JTabbedPaneClass jTabbedPaneClass;

    private DraftTreeImplementation draftTreeImplementation;

    public AddState() {
        roomElementFactory = new RoomElementFactory();
        roomElementFactory.registerPrototype("bojler", new Bojler("Default Bojler", null));
        roomElementFactory.registerPrototype("lavabo", new Lavabo("Default Lavabo", null));
        roomElementFactory.registerPrototype("kada", new Kada("Default Kada", null));
        roomElementFactory.registerPrototype("krevet", new Krevet("Default Krevet", null));
        roomElementFactory.registerPrototype("ormar", new Ormar("Default Ormar", null));
        roomElementFactory.registerPrototype("sto", new Sto("Default Sto", null));
        roomElementFactory.registerPrototype("vesMasina", new VesMasina("Default VesMasina", null));
        roomElementFactory.registerPrototype("wcSolja", new WCSolja("Default WcSolja", null));
        roomElementFactory.registerPrototype("vrata", new Vrata("Default vrata", null));
    }

    @Override
    public void naKlikMisa(RoomView roomView, int x, int y) {
        jTreeActions = MainFrame.getUniqueInstance().getDraftTreeImplementation().getjTreeActions();
        draftTreeImplementation = MainFrame.getUniqueInstance().getDraftTreeImplementation();
        jTabbedPaneClass = MainFrame.getUniqueInstance().getJTabbedPaneClass();

        try {
            String elementType = roomView.getMediator().getStaHocemoDaPravimoRoomElement();
            RoomElementSize size = RoomElementSize.promptForSize();

            // Kreiramo i izvršavamo AddElementCommand
            AddElementCommand addElementCommand = new AddElementCommand(roomView, elementType, x, y, size, roomElementFactory);
            addElementCommand.execute();

            // Dodajemo komandu u istoriju komandi za undo/red
            roomView.getCommandHistory().executeCommand(
                    addElementCommand
            );
            // Dodajemo element u JTree
            jTreeActions.addRoomElementToTree(roomView.getRoom(), addElementCommand.getElement());

        } catch (Exception e) {
            System.out.println("Greška pri dodavanju elementa: " + e.getMessage());
        }
    }

    @Override
    public void naMouseDragged(RoomView roomView, int x, int y) {
        // Nije implementirano
    }

    @Override
    public void naPretisnutoDugme(RoomView roomView, String key) {
        // Nije implementirano
    }

    @Override
    public void naZoom(RoomView roomView, double zoomFactor) {
        // Nije implementirano
    }

    public RoomElementFactory getRoomElementFactory() {
        return roomElementFactory;
    }

    public JTreeActions getjTreeActions() {
        return jTreeActions;
    }

    public void setjTreeActions(JTreeActions jTreeActions) {
        this.jTreeActions = jTreeActions;
    }
}
