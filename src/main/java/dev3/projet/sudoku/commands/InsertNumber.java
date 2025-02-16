package dev3.projet.sudoku.commands;

import dev3.projet.sudoku.model.Position;
import dev3.projet.sudoku.model.SudokuGrid;

public class InsertNumber implements Command{

    int number;
    Position position;
    SudokuGrid grid;

    public InsertNumber(SudokuGrid grid, Position position, int number) {
        this.grid = grid;
        this.position = position;
        this.number = number;
    }
    @Override
    public void execute() {

    }

    @Override
    public void unexecute() {

    }

    public Position getPosition(){
        return position;
    }

    public int getNumber() {
        return number;
    }
}
