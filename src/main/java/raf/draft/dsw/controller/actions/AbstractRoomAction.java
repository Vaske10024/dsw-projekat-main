package raf.draft.dsw.controller.actions;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public abstract class AbstractRoomAction extends AbstractAction {
    public Icon loadIcon(String path){
        Icon icon = null;
        URL ImageURL = getClass().getResource(path);
        if(ImageURL != null)
        {
            Image img = new ImageIcon(ImageURL).getImage();
            Image newImg = img.getScaledInstance(40, 30, Image.SCALE_DEFAULT);
            icon = new ImageIcon(newImg);
        }
        else
        {
            System.err.println("File " + "images/exit.png" + " not found");
        }
        return icon;
    }

}
