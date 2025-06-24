package raf.draft.dsw.gui.swing;

import raf.draft.dsw.controller.actions.*;

public class ActionMenagerView {
    private ExitAction exitAction = new ExitAction() ;
    private AboutUsAction aboutUsAction = new AboutUsAction();
    private AddProjectAction addProjectAction = new AddProjectAction();
    private RemoveAction removeAction = new RemoveAction();
    private RenameAction renameAction = new RenameAction();
    private KakoRadi kakoRadi= new KakoRadi();
    private SetAuthorAction setAuthorAction = new SetAuthorAction();


    public KakoRadi getKakoRadi() {
        return kakoRadi;
    }

    public void setKakoRadi(KakoRadi kakoRadi) {
        this.kakoRadi = kakoRadi;
    }

    public ExitAction getExitAction() {
        return exitAction;
    }

    public AboutUsAction getAboutUsAction() {
        return aboutUsAction;
    }

    public AddProjectAction getAddProjectAction() {
        return addProjectAction;
    }
    public RemoveAction getRemoveAction(){return removeAction;}
    public RenameAction getRenameAction(){return renameAction;}
    public SetAuthorAction getSetAuthorAction(){return setAuthorAction;}
}
