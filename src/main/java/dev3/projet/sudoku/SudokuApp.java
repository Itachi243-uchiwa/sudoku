package dev3.projet.sudoku;


import dev3.projet.sudoku.controller.SudokuController;
import dev3.projet.sudoku.model.SudokuModel;
import dev3.projet.sudoku.view.SudokuView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Point d'entrée principal de l'application Sudoku
 */
public class SudokuApp extends Application {
    @Override
    public void start(Stage primaryStage) {

        SudokuController controller = new SudokuController();

        // Configuration de la fenêtre principale
        Scene scene = new Scene(controller.getView().getRoot(), 600, 700);
        scene.getStylesheets().add(getClass().getResource("/styles/modern-style.css").toExternalForm());

        primaryStage.setTitle("Sudoku Martinez");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
