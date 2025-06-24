package raf.draft.dsw.gui.swing.state;

import raf.draft.dsw.gui.swing.state.concreteStates.*;

public class StateMenager {
    //Sa curretState radimo za svaki state;
    //Ne trebaju geteri za svaki state samo seteri i to od curret na izabrani tj. custom.
    private State curretState;
    private AddState addState;
    private DeleteState deleteState;
    private EditRoomState editRoomState;
    private EditState editState;
    private MoveState moveState;
    private ResizeState resizeState;
    private RotateState rotateState;
    private SelectState selectState;
    private ZoomState zoomState;
    private CopyPasteState copyPasteState;

    public StateMenager() {
        initializeStates();
    }

    private void initializeStates(){
        addState = new AddState();
        deleteState = new DeleteState();
        editRoomState  = new EditRoomState();
        editState = new EditState();
        moveState = new MoveState();
        resizeState = new ResizeState();
        rotateState = new RotateState();
        selectState = new SelectState();
        zoomState = new ZoomState();
        copyPasteState= new CopyPasteState(addState);
        curretState= addState;
    }
    public State getCurretState(){
        return curretState;
    }
    public void setAddState(){
        System.out.println("Postavljeno addstate");
        curretState = addState;
    }
    public void setEditRoomState(){
        curretState = editRoomState;
    }
    public void setDeleteState(){
        curretState = deleteState;
    }
    public void setEditState(){
        curretState = editState;
    }
    public void setMoveState(){
        curretState = moveState;}
    public void setResizeState(){
        curretState = resizeState;
    } public void setRotateState(){
        curretState = rotateState;
    } public void setSelectState(){
        curretState = selectState;
    } public void setZoomState(){
        curretState = zoomState;
    }
    public void setCurretState(State curretState){
        this.curretState = curretState;
    }

    public void setCopyPasteState(){
        curretState = copyPasteState;
    }

    public AddState getAddState(){
        return addState;
    }
}
