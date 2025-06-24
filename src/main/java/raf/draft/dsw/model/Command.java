package raf.draft.dsw.model;

public interface Command {
    void execute();
    void undo();
    void redo();
}
