package dev3.projet.sudoku.view;

import dev3.projet.sudoku.controller.SudokuController;
import dev3.projet.sudoku.model.Position;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

/**
 * Vue du Sudoku - Gère l'interface utilisateur
 */
public class SudokuView {
    private GridPane gridPane;
    private TextField[][] cells;
    private VBox root;
    private Button undoButton;
    private Button redoButton;
    private Button newGameButton;
    private Button validateButton;
    private Button solvedButton;
    private Label victoryMessage;
    private SudokuController controller;

    public SudokuView() {
        initializeUI();
    }

    public void setController(SudokuController controller) {
        this.controller = controller;
    }

    private void initializeUI() {
        root = new VBox(10);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);

        // Création de la grille
        gridPane = new GridPane();
        gridPane.getStyleClass().add("grid-pane");
        gridPane.setAlignment(Pos.CENTER);

        cells = new TextField[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                TextField cell = createCell(i, j);
                cells[i][j] = cell;
                gridPane.add(cell, j, i);

                if (i % 3 == 0) {
                    cell.getStyleClass().add("cell-top-border");
                }
                if (j % 3 == 0) {
                    cell.getStyleClass().add("cell-left-border");
                }
            }
        }

        // Création des boutons
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);

        undoButton = new Button("Annuler");
        redoButton = new Button("Refaire");
        newGameButton = new Button("Nouvelle Partie");
        validateButton = new Button("Valider");
        solvedButton = new Button("Solution");

        undoButton.setId("undoButton");
        redoButton.setId("redoButton");
        newGameButton.setId("newGameButton");
        validateButton.setId("validateButton");


        buttonBox.getChildren().addAll(newGameButton, validateButton, solvedButton);

        // Champ pour afficher le message de victoire
        victoryMessage = new Label();
        victoryMessage.setFont(Font.font(18));
        victoryMessage.setAlignment(Pos.CENTER);
        victoryMessage.setVisible(false);

        root.getChildren().addAll(gridPane, buttonBox, victoryMessage);
    }

    private TextField createCell(int row, int col) {
        TextField cell = new TextField();
        cell.setPrefSize(50, 50);
        cell.setAlignment(Pos.CENTER);
        cell.setFont(Font.font(20));
        cell.getStyleClass().add("sudoku-cell");

        cell.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                cell.setText(newValue.replaceAll("[^\\d]", ""));
            }
            if (newValue.length() > 1) {
                cell.setText(newValue.substring(0, 1));
            }


            if (controller != null) {
                try {
                    int number = newValue.isEmpty() ? 0 : Integer.parseInt(newValue);
                    controller.setNumberInModel(new Position(row, col), number);
                } catch (NumberFormatException e) {

                    System.err.println("Erreur de format : " + e.getMessage());
                }
            }
        });

        return cell;
    }

    public void displayInitialGrid(int[][] initialGrid) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int value = initialGrid[i][j];
                TextField cell = cells[i][j];

                if (value != 0) {
                    cell.setText(String.valueOf(value));
                    cell.setEditable(false);
                    cell.getStyleClass().add("fixed-cell");
                } else {
                    cell.setText("");
                    cell.setEditable(true);
                    cell.getStyleClass().add("sudoku-cell");
                }
            }
        }

    }

    // Getters pour les éléments de l'interface
    public VBox getRoot() {
        return root;
    }

    public TextField getCell(int row, int col) {
        return cells[row][col];
    }

    public Button getSolvedButton() {
        return solvedButton;
    }

    public Button getUndoButton() {
        return undoButton;
    }

    public Button getRedoButton() {
        return redoButton;
    }

    public Button getNewGameButton() {
        return newGameButton;
    }

    public Button getValidateButton() {
        return validateButton;
    }

    public void updateCell(Position position, int number) {
        cells[position.row()][position.column()].setText(number == 0 ? "" : String.valueOf(number));
    }

    public void undoUpdate(Position position, int previousNumber) {
        updateCell(position, previousNumber);
    }

    public void redoUpdate(Position position, int nextNumber) {
        updateCell(position, nextNumber);
    }

    public void displayVictoryMessage(String message) {
        victoryMessage.setText(message);
        victoryMessage.setVisible(true);
    }
}
