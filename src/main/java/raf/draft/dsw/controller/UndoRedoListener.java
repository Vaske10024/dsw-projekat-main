package raf.draft.dsw.controller;

public interface UndoRedoListener {
    void updateUndoRedo(boolean canUndo, boolean canRedo);
}
