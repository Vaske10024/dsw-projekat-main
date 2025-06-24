package raf.draft.dsw.controller.Command;

import com.sun.tools.javac.Main;
import raf.draft.dsw.controller.UndoRedoListener;
import raf.draft.dsw.gui.swing.MainFrame;
import raf.draft.dsw.gui.swing.RoomView;
import raf.draft.dsw.model.Command;

import java.util.Stack;



import java.util.Stack;

public class CommandHistory {
    private final Stack<Command> undoStack = new Stack<>();
    private final Stack<Command> redoStack = new Stack<>();
    private UndoRedoListener listener;

    public void setUndoRedoListener(UndoRedoListener listener) {
        this.listener = listener;
        notifyListener();
    }

    public void executeCommand(Command command) {
        command.execute();
        undoStack.push(command);
        redoStack.clear();
        notifyListener();
    }

    public void undo() {
        if (!undoStack.isEmpty()) {
            Command command = undoStack.pop();
            command.undo();
            redoStack.push(command);
            notifyListener();
        }
    }

    public void redo() {
        if (!redoStack.isEmpty()) {
            Command command = redoStack.pop();
            command.redo();
            undoStack.push(command);
            notifyListener();
        }
    }

    private void notifyListener() {
        if (listener != null) {
            listener.updateUndoRedo(!undoStack.isEmpty(), !redoStack.isEmpty());
        }
    }
}
