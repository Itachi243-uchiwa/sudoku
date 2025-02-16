package dev3.projet.sudoku.model;

import java.util.*;


class SudokuGenerator {

    private List<Position> fixedPositions;
    public void generateSolution(int[][] grid) {
        fixedPositions = new ArrayList<>();
        fillDiagonal(grid);
        solveSudoku(grid);
    }

    private void fillDiagonal(int[][] grid) {
        for (int i = 0; i < 9; i += 3) {
            fillBox(grid, new Position(i,i));
        }
    }

    private void fillBox(int[][] grid, Position pos) {
        List<Integer> numbers = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9));
        Collections.shuffle(numbers);
        int index = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                grid[pos.row() + i][pos.column() + j] = numbers.get(index++);
            }
        }
    }

        /**
     * Solves the Sudoku puzzle using a backtracking algorithm.
     * This method attempts to fill in the empty cells of the Sudoku grid
     * with valid numbers, backtracking when necessary.
     *
     * @param grid The 9x9 Sudoku grid represented as a 2D integer array.
     *             This array is modified in-place during the solving process.
     * @return true if a solution is found, false if no solution exists.
     */
     public int[][] solveSudoku(int[][] grid) {
            Position emptyCell = findEmptyCell(grid);
            if (emptyCell == null) return copyGrid(grid);

            int row = emptyCell.row();
            int col = emptyCell.column();

            List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
            Collections.shuffle(numbers);

            for (int num : numbers) {
                if (isValid(grid, emptyCell, num)) {
                    grid[row][col] = num;
                    int[][] solution = solveSudoku(grid);
                    if (solution != null) return solution;
                    grid[row][col] = 0;
                }
            }
            return null;
        }
    private int[][] copyGrid(int[][] grid) {
        int[][] copy = new int[9][9];
        for (int i = 0; i < 9; i++) {
            System.arraycopy(grid[i], 0, copy[i], 0, 9);
        }
        return copy;
    }



    /**
     * Finds the first empty cell in the Sudoku grid.
     * An empty cell is represented by the value 0.
     *
     * @param grid The 9x9 Sudoku grid represented as a 2D integer array.
     * @return An integer array of length 2 containing the row and column indices of the first empty cell,
     *         or null if no empty cell is found.
     */
    private Position findEmptyCell(int[][] grid) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (grid[i][j] == 0) {
                    return new Position(i, j);
                }
            }
        }
        return null;
    }

        /**
     * Checks if it's valid to place a number in a specific position on the Sudoku grid.
     * This method verifies that the number doesn't already exist in the same row, column, or 3x3 box.
     *
     * @param grid The 9x9 Sudoku grid represented as a 2D integer array.
     * @param pos  The Position object representing the row and column where the number is to be placed.
     * @param num  The number to be checked for validity (1-9).
     * @return     true if it's valid to place the number at the given position, false otherwise.
     */
        boolean isValid(int[][] grid, Position pos, int num) {
        for (int j = 0; j < 9; j++) {
            if (grid[pos.row()][j] == num) return false;
        }
        for (int i = 0; i < 9; i++) {
            if (grid[i][pos.column()] == num) return false;
        }
        int boxRow = pos.row() - pos.row() % 3;
        int boxCol = pos.column() - pos.column() % 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grid[boxRow + i][boxCol + j] == num) return false;
            }
        }
        return true;
    }

    /**
     * Creates a Sudoku puzzle by removing a random number of cells from a completed grid.
     * This method modifies the input grid and sets the corresponding cells in the fixed array.
     *
     * @param grid  A 9x9 2D array representing the completed Sudoku grid. This array will be modified
     *              to create the puzzle by removing some values.
     * @param fixed A 9x9 2D boolean array that will be set to indicate which cells in the grid
     *              are fixed (true) or empty/modifiable (false).
     */
    public List<Position> createPuzzle(int[][] grid, boolean[][] fixed) {
        Random random = new Random();
        int cellsToRemove = 40 + random.nextInt(21);

        while (cellsToRemove > 0) {
            int row = random.nextInt(9);
            int col = random.nextInt(9);

            if (grid[row][col] != 0) {
                grid[row][col] = 0;
                fixed[row][col] = false;
                cellsToRemove--;
            }
        }

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (grid[i][j] != 0) {
                    fixed[i][j] = true;
                    fixedPositions.add(new Position(i, j));

                }
            }
        }
        return fixedPositions;
    }
}
