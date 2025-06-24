package raf.draft.dsw.gui.swing;

import raf.draft.dsw.controller.actions.*;

import javax.swing.*;
import java.awt.*;

public class MyToolBar extends JToolBar {
    public MyToolBar(ActionMenagerView actionMenagerView){
        super(HORIZONTAL);
        setBackground(new Color(0xe6ecf5));
        setFloatable(false);

        ExitAction ea = actionMenagerView.getExitAction();
        AboutUsAction au = actionMenagerView.getAboutUsAction();
        AddProjectAction app = actionMenagerView.getAddProjectAction();
        RemoveAction ra = actionMenagerView.getRemoveAction();
        RenameAction rna = actionMenagerView.getRenameAction();
        KakoRadi kakoRadi= actionMenagerView.getKakoRadi();
        SetAuthorAction saa = new SetAuthorAction();
        OpenProjectAction oppa = new OpenProjectAction();
        SaveAsAction sa = new SaveAsAction();
        SaveAction sa2 = new SaveAction();
        OpenRoomAction openRoomAction = new OpenRoomAction();
        SaveRoomAction sa3 = new SaveRoomAction();
        OrganizeRoomAction organizeRoomAction = new OrganizeRoomAction();

        add(ea);
        add(au);
        add(app);
        add(ra);
        add(rna);
        add(saa);
        add(kakoRadi);
        add(oppa);
        add(sa);
        add(sa2);
        add(openRoomAction);
        add(sa3);
        add(organizeRoomAction);
    }
}
