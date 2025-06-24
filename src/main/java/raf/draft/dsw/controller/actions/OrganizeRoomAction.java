package raf.draft.dsw.controller.actions;

import com.sun.tools.javac.Main;
import raf.draft.dsw.gui.swing.MainFrame;
import raf.draft.dsw.gui.swing.PopUpWindows.RoomOrganizer;

import java.awt.event.ActionEvent;


public class OrganizeRoomAction extends AbstractRoomAction {

    public OrganizeRoomAction() {
        putValue(NAME, "OrganizeRoom");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(MainFrame.getUniqueInstance().getMediator().getRoomView()==null){
            System.out.println("Molimo kreirajte sobu");
        }
        else {
            RoomOrganizer roomOrganizer = new RoomOrganizer();
            roomOrganizer.setVisible(true); // Prikazuje prozor
    }
}}
