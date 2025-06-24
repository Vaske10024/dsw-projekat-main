package raf.draft.dsw.controller.actions.stateActions;

import com.sun.tools.javac.Main;
import raf.draft.dsw.controller.actions.AbstractRoomAction;
import raf.draft.dsw.gui.swing.MainFrame;
import raf.draft.dsw.gui.swing.PopUpWindows.RotationChoice;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class RotateStateAction extends AbstractRoomAction {
    public RotateStateAction() {

        putValue(NAME, "Rotate");
        putValue(SMALL_ICON, loadIcon("/images/rotate.png"));
        putValue(SHORT_DESCRIPTION, "Rotiraj");

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String chosenOption = RotationChoice.showOptionsDialog();
        if(chosenOption==null){
            MainFrame.getUniqueInstance().getMediator().getStateMenager().setCurretState(null);
            return;
        }

        MainFrame.getUniqueInstance().getMediator().getStateMenager().setRotateState();
        if(chosenOption=="Leva rotacija"){
            MainFrame.getUniqueInstance().getMediator().setTipRotacije("levo");
        }
        else if(chosenOption=="Desna rotacija"){
            MainFrame.getUniqueInstance().getMediator().setTipRotacije("desno");
        }

}}
