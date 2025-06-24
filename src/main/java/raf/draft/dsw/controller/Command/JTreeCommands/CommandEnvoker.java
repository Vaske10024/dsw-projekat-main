package raf.draft.dsw.controller.Command.JTreeCommands;

import raf.draft.dsw.model.Command;

import java.util.Stack;

public class CommandEnvoker {
    private Stack<Command> commandHistory = new Stack<>();

    public void executeCommand(Command command) {
        command.execute();
        commandHistory.push(command);
    }

    public void undo() {
        if (!commandHistory.isEmpty()) {
            Command lastCommand = commandHistory.pop();
            lastCommand.undo();
        }
    }
}
