package raf.draft.dsw.gui.swing;

import raf.draft.dsw.controller.actions.stateActions.*;

public class ActionMenagerState {
    AddStateAction addStateAction= new AddStateAction();
    CopyPaseStateAction copyPaseStateAction= new CopyPaseStateAction();
    DeleteStateAction deleteStateAction= new DeleteStateAction();
    EditStateAction editStateAction= new EditStateAction();
    MoveStateAction moveStateAction= new MoveStateAction();
    ResizeStateAction resizeStateAction= new ResizeStateAction();
    RotateStateAction rotateStateAction= new RotateStateAction();
    SelectStateAction selectStateAction= new SelectStateAction();
    ZoomStateAction zoomStateAction= new ZoomStateAction();

    public AddStateAction getAddStateAction() {
        return addStateAction;
    }

    public CopyPaseStateAction getCopyPaseStateAction() {
        return copyPaseStateAction;
    }

    public DeleteStateAction getDeleteStateAction() {
        return deleteStateAction;
    }

    public EditStateAction getEditStateAction() {
        return editStateAction;
    }


    public MoveStateAction getMoveStateAction() {
        return moveStateAction;
    }

    public ResizeStateAction getResizeStateAction() {
        return resizeStateAction;
    }

    public RotateStateAction getRotateStateAction() {
        return rotateStateAction;
    }

    public SelectStateAction getSelectStateAction() {
        return selectStateAction;
    }

    public ZoomStateAction getZoomStateAction() {
        return zoomStateAction;
    }
}
