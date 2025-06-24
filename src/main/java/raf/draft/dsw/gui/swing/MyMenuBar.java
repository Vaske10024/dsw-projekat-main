package raf.draft.dsw.gui.swing;

import raf.draft.dsw.controller.actions.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class MyMenuBar extends JMenuBar {
    public MyMenuBar(){
        setBackground(new Color(0xe6ecf5));
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        ExitAction ea = new ExitAction();
        RemoveAction ra = new RemoveAction();
        RenameAction rna = new RenameAction();
        AddProjectAction apa = new AddProjectAction();
        SetAuthorAction saa = new SetAuthorAction();
        fileMenu.add(ea);
        fileMenu.add(ra);
        fileMenu.add(rna);
        fileMenu.add(saa);
        fileMenu.add(apa);
        add(fileMenu);
    }
}
