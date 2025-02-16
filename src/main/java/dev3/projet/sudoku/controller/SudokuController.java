package dev3.projet.sudoku.controller;

import dev3.projet.sudoku.model.SudokuModel;
import dev3.projet.sudoku.Observer.Observer;
import dev3.projet.sudoku.Observer.SudokuEvent;
import dev3.projet.sudoku.model.Position;
import dev3.projet.sudoku.view.SudokuView;

import java.util.List;

public class SudokuController implements Observer {
    private SudokuModel model;
    private SudokuView view;

    public SudokuController() {
        this.view = new SudokuView();
        this.model = new SudokuModel();
        model.registerObserver(this);
        initializeController();
        view.setController(this);
    }

    public SudokuView getView() {
        return view;
    }

    private void initializeController() {
        model.init();
        view.getRedoButton().setOnAction(e -> undo());
        view.getUndoButton().setOnAction(e -> redo());
        view.getNewGameButton().setOnAction(e -> newGame());
        view.getValidateButton().setOnAction(e -> validate());
        view.getSolvedButton().setOnAction(e ->solved());

    }



    private void undo(){
        model.undo();
    }

    private void redo(){
        model.redo();
    }

    private void validate(){
            if (model.validate()) {
                view.displayVictoryMessage("Félicitations ! La grille est correcte.");
            } else {
                view.displayVictoryMessage("La grille contient des erreurs. Veuillez vérifier vos chiffres.");
            }

    }
    public void solved(){
        model.solved();
    }

    // Méthode pour démarrer une nouvelle partie
    public void newGame() {
        model = new SudokuModel();
        model.registerObserver(this);
        initializeController();
    }

    // Méthode appelée par la vue pour définir un nombre dans la grille
    public void setNumberInModel(Position pos, int number) {
        model.setNumber(pos, number); // Le modèle gère la logique
    }

    @Override
    public void update(SudokuModel sudoku, SudokuEvent event) {
        // Met à jour la vue en fonction de l'événement
        switch (event.getEvent()) {
            case GAMESTART:
                view.displayInitialGrid(model.getGrid());
                break;
            case NUMBER_UPDATED:
                // L'événement contient des données spécifiques, donc on met à jour une seule cellule
                Position pos = event.getEventData("position", Position.class);
                int number = (Integer) event.getEventData("number", Number.class);
                view.updateCell(pos, number);
                break;
            case UNDO:
                Position undoPos = event.getEventData("position", Position.class);
                int undoNumber = (Integer) event.getEventData("number", Number.class);
                view.undoUpdate(undoPos, undoNumber);
                break;
            case REDO:
                Position redoPos = event.getEventData("position", Position.class);
                int redoNumber = (Integer) event.getEventData("number", Number.class);
                view.redoUpdate(redoPos, redoNumber);
                break;
            case SOLVED:
                view.displayInitialGrid(model.getGrid());
                view.displayVictoryMessage("Vous avez gagnee");
                break;
        }
    }
}
