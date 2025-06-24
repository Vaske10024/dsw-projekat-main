package raf.draft.dsw.core;

import com.sun.tools.javac.Main;
import raf.draft.dsw.gui.swing.MainFrame;

import java.awt.*;

import static javax.swing.text.StyleConstants.setBackground;

public class ApplicationFramework {
    //buduca polja za model celog projekta
    private static ApplicationFramework applicationFramework;

    private ApplicationFramework(){
        initialize();
    }

    private static void initialize(){
        MainFrame.getUniqueInstance().setVisible(true);
    }

    public static ApplicationFramework getApplicationFramework() {
        if(applicationFramework ==null){
            applicationFramework = new ApplicationFramework();
        }
        return applicationFramework;
    }
}
