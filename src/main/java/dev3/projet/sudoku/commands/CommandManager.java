package dev3.projet.sudoku.commands;

import dev3.projet.sudoku.model.SudokuException;

import java.util.Stack;

public class CommandManager {

    private Stack<Command> undoStack = new Stack<>();
    private Stack<Command> redoStack = new Stack<>();

    /**
     * Executes the provided command and updates the undo/redo stacks to reflect the new state.
     * This method clears the redo stack after executing a new command to maintain a consistent history.
     *
     * @param command The command to be executed. It implements the {@code Command} interface and must define
     *                the logic for execution and unexecution. The command is also pushed to the undo stack
     *                for potential reversal.
     */
    public void executeCommand(Command command) {
        command.execute();
        undoStack.push(command);
        redoStack.clear();
    }

    public Command undo() {
        if (!canUndo()) {
            throw new SudokuException("Can't undo");
        }
        Command command = undoStack.pop();
        command.unexecute();
        redoStack.push(command);
        return command;
    }

    public Command redo() {
        if (!canRedo()) {
            throw new SudokuException("Can't redo");
        }
        Command command = redoStack.pop();
        command.execute();
        undoStack.push(command);
        return command;
    }

    public boolean canUndo() {
        return !undoStack.isEmpty();
    }

    public boolean canRedo() {
        return !redoStack.isEmpty();
    }
}

