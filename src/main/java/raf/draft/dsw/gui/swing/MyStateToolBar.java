package raf.draft.dsw.gui.swing;

import raf.draft.dsw.controller.actions.*;
import raf.draft.dsw.controller.actions.stateActions.*;

import javax.swing.*;
import java.awt.*;

public class MyStateToolBar extends JToolBar {
    public MyStateToolBar(ActionMenagerState actionMenagerState){

        super(VERTICAL);
        setBackground(new Color(0xe6ecf5));
        setFloatable(false);
        //gde si
        AddStateAction addStateAction= actionMenagerState.addStateAction;
        CopyPaseStateAction copyPaseStateAction= actionMenagerState.copyPaseStateAction;
        DeleteStateAction deleteStateAction= actionMenagerState.deleteStateAction;
        EditStateAction editStateAction= actionMenagerState.editStateAction;
        MoveStateAction moveStateAction= actionMenagerState.moveStateAction;
        ResizeStateAction resizeStateAction= actionMenagerState.resizeStateAction;
        RotateStateAction rotateStateAction= actionMenagerState.rotateStateAction;
        SelectStateAction selectStateAction= actionMenagerState.selectStateAction;
        ZoomStateAction zoomStateAction= actionMenagerState.zoomStateAction;


        add(addStateAction);
        add(selectStateAction);
        add(deleteStateAction);
        add(editStateAction);
        add(moveStateAction);
        add(resizeStateAction);
        add(rotateStateAction);
        add(copyPaseStateAction);
        add(zoomStateAction);

    }
}
