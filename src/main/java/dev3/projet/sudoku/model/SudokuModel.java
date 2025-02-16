package dev3.projet.sudoku.model;

import dev3.projet.sudoku.Observer.Observable;
import dev3.projet.sudoku.Observer.ObservableEvent;
import dev3.projet.sudoku.Observer.Observer;
import dev3.projet.sudoku.Observer.SudokuEvent;
import dev3.projet.sudoku.commands.Command;
import dev3.projet.sudoku.commands.CommandManager;
import dev3.projet.sudoku.commands.InsertNumber;

import java.util.*;

/**
 * Modèle du Sudoku - Gère la logique du jeu et les commandes
 */
public class SudokuModel implements Observable {
    private SudokuGrid grid;
    private CommandManager invoker;
    private List<Observer> observers;

    public SudokuModel() {
        grid = new SudokuGrid();
        invoker = new CommandManager();
        observers = new ArrayList<>();
    }
    public void init(){
        notifyObservers(new SudokuEvent(ObservableEvent.GAMESTART));
    }

    public void setNumber(Position pos, int number) {
        if (!isFixed(pos)) {
            grid.setNumber(pos, number);
            Command command = new InsertNumber(grid, pos, number);
            invoker.executeCommand(command);
            notifyObservers(new SudokuEvent(ObservableEvent.NUMBER_UPDATED)
                   .addData("position", pos)
                   .addData("number", number));

        }
    }

    public int getNumber(Position pos) {
        return grid.getNumber(pos);
    }

    public boolean isFixed(Position pos) {
        return grid.isFixed(pos);
    }

    public void undo() {
        Command command = invoker.undo();
        if (command!= null) {
            InsertNumber insertUndo = (InsertNumber) command;
            Position pos = insertUndo.getPosition();
            int number = insertUndo.getNumber();

            notifyObservers(new SudokuEvent(ObservableEvent.UNDO)
             .addData("position", pos)
             .addData("number", number));
        }
    }

    public void redo() {
        Command command = invoker.redo();
        if (command!= null) {
            InsertNumber insertRedo = (InsertNumber) command;
            Position pos = insertRedo.getPosition();
            int number = insertRedo.getNumber();

            notifyObservers(new SudokuEvent(ObservableEvent.REDO)
                   .addData("position", pos)
                   .addData("number", number));
        }
    }

    public int[][] getGrid() {
       return grid.getGrid();
    }
    public boolean validate() {
        boolean isSolved = grid.isWinning(grid.getGrid());
        if (isSolved) {
            notifyObservers(new SudokuEvent(ObservableEvent.SOLVED));
        }
        return isSolved;
    }

    public void solved() {
        grid.solvedSudoku();
        notifyObservers(new SudokuEvent(ObservableEvent.SOLVED));
    }
    @Override
    public void registerObserver(Observer o) {
        observers.add(o);

    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);

    }

    @Override
    public void notifyObservers(SudokuEvent event) {
        for (Observer observer : observers) {
            observer.update(this, event);
        }

    }
}
