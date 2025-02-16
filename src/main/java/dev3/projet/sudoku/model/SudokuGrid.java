package dev3.projet.sudoku.model;


import java.util.List;

public class SudokuGrid {
    private int[][] grid;
    private boolean[][] fixed;
    SudokuGenerator generator;
    private List<Position> fixedPositions;

    public SudokuGrid() {
        grid = new int[9][9];
        fixed = new boolean[9][9];
        initializeGrid();
    }

    private void initializeGrid() {
        generator = new SudokuGenerator();
        generator.generateSolution(grid);
        fixedPositions = generator.createPuzzle(grid, fixed);
    }

    public int getNumber(Position pos ) {
        return grid[pos.row()][pos.column()];
    }

    public void setNumber(Position pos, int number) {
        generator.isValid(grid, pos, number);
        grid[pos.row()][pos.column()] = number;
    }

    public void solvedSudoku(){
        int[][] solution = generator.solveSudoku(grid);
        if (solution != null){
            grid = solution;

        }
    }

    public boolean isFixed(Position pos) {
        return fixed[pos.row()][pos.column()];
    }
    public boolean isWinning(int[][] grid) {

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (grid[i][j] == 0) return false;
            }
        }


        return isValidSudoku(grid);
    }

    private boolean isValidSudoku(int[][] grid) {
        for (int i = 0; i < 9; i++) {
            if (!isValidGroup(grid[i])) return false;

            int[] col = new int[9];
            for (int j = 0; j < 9; j++) {
                col[j] = grid[j][i];
            }
            if (!isValidGroup(col)) return false;
        }

        // Vérifier les blocs 3x3
        for (int row = 0; row < 9; row += 3) {
            for (int col = 0; col < 9; col += 3) {
                if (!isValidBlock(grid, row, col)) return false;
            }
        }
        return true;
    }

    private boolean isValidGroup(int[] group) {
        boolean[] seen = new boolean[10];
        for (int num : group) {
            if (num < 1 || num > 9 || seen[num]) return false;
            seen[num] = true;
        }
        return true;
    }

    private boolean isValidBlock(int[][] grid, int startRow, int startCol) {
        boolean[] seen = new boolean[10];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int num = grid[startRow + i][startCol + j];
                if (num < 1 || num > 9 || seen[num]) return false;
                seen[num] = true;
            }
        }
        return true;
    }
    public int[][] getGrid(){
        return grid;

    }

}