package dev3.projet.sudoku.Observer;

public interface Observable {

    void registerObserver(Observer o);

    void removeObserver(Observer o);

    void notifyObservers(SudokuEvent event);
}