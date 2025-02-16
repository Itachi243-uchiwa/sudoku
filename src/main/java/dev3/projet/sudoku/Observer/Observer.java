package dev3.projet.sudoku.Observer;

import dev3.projet.sudoku.model.SudokuModel;

public interface Observer {
    void update(SudokuModel sudoku, SudokuEvent event);
}