package raf.draft.dsw.gui.swing.state;

import raf.draft.dsw.gui.swing.MainFrame;
import raf.draft.dsw.gui.swing.PopUpWindows.RoomElementSize;
import raf.draft.dsw.gui.swing.RoomView;
import raf.draft.dsw.gui.swing.state.concreteStates.*;
import raf.draft.dsw.model.structures.Room;
import view.painters.Painter;

import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Mediator {
    private StateMenager stateMenager;
    private RoomView roomView;
    private String staHocemoDaPravimoRoomElement; // Koristi se u AddState
    private ArrayList<Painter> selectedNodes; // Lista selektovanih elemenata
    private String tipRotacije;
    private ArrayList<Painter> copiedElements;
    public String getTipRotacije() {
        return tipRotacije;
    }

    public void setTipRotacije(String tipRotacije) {
        this.tipRotacije = tipRotacije;
    }

    public Mediator() {
        this.stateMenager = new StateMenager();
        this.selectedNodes = new ArrayList<>();
        this.copiedElements = new ArrayList<>();
    }

    /**
     * Startovanje akcije u zavisnosti od trenutnog stanja.
     * @param mouseEvent događaj miša
     */
    public void startAction(MouseEvent mouseEvent) {
        if (stateMenager.getCurretState() == null) {
            System.out.println("Molimo vas izaberite stanje pre nego što počnete akciju.");
            return;
        }

        int eventID = mouseEvent.getID();

        // Ispis trenutnog stanja samo za specifične događaje
        if (eventID == MouseEvent.MOUSE_CLICKED || eventID == MouseEvent.MOUSE_PRESSED) {
            System.out.println("Trenutno stanje: " + stateMenager.getCurretState().getClass().getSimpleName());
        }

        if (stateMenager.getCurretState() instanceof AddState) {
            if(eventID==MouseEvent.MOUSE_CLICKED) {
                stateMenager.getCurretState().naKlikMisa(roomView, mouseEvent.getX(), mouseEvent.getY());
                roomView.setElementKojiHocemoDaDodamo(staHocemoDaPravimoRoomElement);
            }
        } else if (stateMenager.getCurretState() instanceof SelectState) {
            // Upravljanje akcijama selektovanja
            handleSelectState(mouseEvent, eventID);
        }  else if (stateMenager.getCurretState() instanceof ResizeState) {
            ResizeState resizeState = (ResizeState) stateMenager.getCurretState();

            switch (eventID) {
                case MouseEvent.MOUSE_PRESSED:
                    resizeState.naKlikMisa(roomView, mouseEvent.getX(), mouseEvent.getY());
                    break;
                case MouseEvent.MOUSE_DRAGGED:
                    resizeState.naMouseDragged(roomView, mouseEvent.getX(), mouseEvent.getY());
                    break;
                case MouseEvent.MOUSE_RELEASED:
                    resizeState.naMouseReleased(roomView, mouseEvent.getX(), mouseEvent.getY());
                    break;
            }
        }

        else if(stateMenager.getCurretState() instanceof EditRoomState){
            if(eventID==MouseEvent.MOUSE_CLICKED){
                stateMenager.getCurretState().naKlikMisa(roomView, mouseEvent.getX(), mouseEvent.getY());
            }
        }
        else if(stateMenager.getCurretState() instanceof MoveState){
            if(eventID==MouseEvent.MOUSE_DRAGGED){
                stateMenager.getCurretState().naMouseDragged(roomView,mouseEvent.getX(),mouseEvent.getY());
            }
            if(eventID==MouseEvent.MOUSE_CLICKED){
                stateMenager.getCurretState().naKlikMisa(roomView, mouseEvent.getX(), mouseEvent.getY());
            }
            if(eventID==MouseEvent.MOUSE_PRESSED){
                ((MoveState) stateMenager.getCurretState()).naMousePressed(roomView,mouseEvent.getX(),mouseEvent.getY());
            }
            if(eventID==MouseEvent.MOUSE_RELEASED){
                ((MoveState) stateMenager.getCurretState()).naMouseReleased(roomView,mouseEvent.getX(),mouseEvent.getY());
            }
        }
        else if(stateMenager.getCurretState() instanceof DeleteState){
            if(eventID==MouseEvent.MOUSE_CLICKED){
                stateMenager.getCurretState().naKlikMisa(roomView, mouseEvent.getX(), mouseEvent.getY());
            }
        }
        else if(stateMenager.getCurretState() instanceof RotateState){
            if(eventID== MouseEvent.MOUSE_CLICKED){
                stateMenager.getCurretState().naKlikMisa(roomView, mouseEvent.getX(), mouseEvent.getY());
            }
        } else if (stateMenager.getCurretState() instanceof EditState) {
            if(eventID==MouseEvent.MOUSE_CLICKED)
            stateMenager.getCurretState().naKlikMisa(roomView,mouseEvent.getX(), mouseEvent.getY());
        }
        else if(stateMenager.getCurretState() instanceof CopyPasteState){
            if(eventID==MouseEvent.MOUSE_CLICKED){
                stateMenager.getCurretState().naKlikMisa(roomView, mouseEvent.getX(), mouseEvent.getY());
            }
        }
        else if(stateMenager.getCurretState() instanceof ZoomState){

        }
    }

    /**
     * Upravljanje selekcijom elemenata.
     * @param mouseEvent događaj miša
     * @param eventID tip događaja
     */
    private void handleSelectState(MouseEvent mouseEvent, int eventID) {
        SelectState selectState = (SelectState) stateMenager.getCurretState();

        switch (eventID) {
            case MouseEvent.MOUSE_CLICKED ->
                    selectState.naKlikMisa(roomView, mouseEvent.getX(), mouseEvent.getY());
            case MouseEvent.MOUSE_DRAGGED ->
                    selectState.naMouseDragged(roomView, mouseEvent.getX(), mouseEvent.getY());
            case MouseEvent.MOUSE_PRESSED ->
                    roomView.setStartingPoint(mouseEvent.getPoint()); // Podešavanje početne tačke za pravougaonik
            case MouseEvent.MOUSE_RELEASED ->
                    selectState.naMouseReleased(roomView);
        }
    }


    public void addToSelectedNodes(Painter painter) {
        if (!selectedNodes.contains(painter)) {
            selectedNodes.add(painter);
        }
    }


    public void removeFromSelectedNodes(Painter painter) {
        selectedNodes.remove(painter);
    }

    // Getteri i setteri

    public StateMenager getStateMenager() {
        return stateMenager;
    }

    public void setStateMenager(StateMenager stateMenager) {
        this.stateMenager = stateMenager;
    }

    public RoomView getRoomView() {
        return roomView;
    }

    public void setRoomView(RoomView roomView) {
        this.roomView = roomView;
        if(roomView!=null){
            roomView.getCommandHistory().setUndoRedoListener(MainFrame.getUniqueInstance());
        }

    }

    public String getStaHocemoDaPravimoRoomElement() {
        return staHocemoDaPravimoRoomElement;
    }

    public void setStaHocemoDaPravimoRoomElement(String staHocemoDaPravimoRoomElement) {
        this.staHocemoDaPravimoRoomElement = staHocemoDaPravimoRoomElement;
    }

    public ArrayList<Painter> getSelectedNodes() {
        return selectedNodes;
    }

    public void setSelectedNodes(ArrayList<Painter> selectedNodes) {
        this.selectedNodes = selectedNodes;
    }
}
