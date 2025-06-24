package raf.draft.dsw.controller.actions.stateActions;

import raf.draft.dsw.controller.actions.AbstractRoomAction;
import raf.draft.dsw.gui.swing.MainFrame;
import raf.draft.dsw.gui.swing.PopUpWindows.RoomElementOptions;
import raf.draft.dsw.model.structures.Elementi.RoomElement;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class AddStateAction extends AbstractRoomAction {

    String selectedRoomElement;

    public AddStateAction() {

        putValue(NAME, "AddState");
        putValue(SMALL_ICON, loadIcon("/images/add.png"));
        putValue(SHORT_DESCRIPTION, "AddState");

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        MainFrame.getUniqueInstance().getMediator().getStateMenager().setAddState();
        String chosenOption = RoomElementOptions.showOptionsDialog();
        if(chosenOption==null){
            MainFrame.getUniqueInstance().getMediator().getStateMenager().setCurretState(null);
            return;
        }
        System.out.println("Selected option: " + chosenOption);
        MainFrame.getUniqueInstance().getMediator().setStaHocemoDaPravimoRoomElement(chosenOption);

    }

    @Override
    public boolean accept(Object sender) {
        return super.accept(sender);
    }

    public String getSelectedRoomElement() {
        return selectedRoomElement;
    }

    public void setSelectedRoomElement(String selectedRoomElement) {
        this.selectedRoomElement = selectedRoomElement;
    }
}
