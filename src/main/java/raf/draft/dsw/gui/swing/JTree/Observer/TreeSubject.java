package raf.draft.dsw.gui.swing.JTree.Observer;

import raf.draft.dsw.model.structures.Project;

import java.util.ArrayList;
import java.util.List;

public class TreeSubject {
    private List<ITreeObserver> observers = new ArrayList<>();

    public void addObserver(ITreeObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(ITreeObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers(Project project) {
        for (ITreeObserver observer : observers) {
            observer.updateTabbedPane(project);
        }
    }
}
