module dev3.projet.sudoku {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    opens dev3.projet.sudoku to javafx.fxml;
    exports dev3.projet.sudoku;
    exports dev3.projet.sudoku.model;
    opens dev3.projet.sudoku.model to javafx.fxml;
}