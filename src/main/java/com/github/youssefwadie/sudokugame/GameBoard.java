package com.github.youssefwadie.sudokugame;

import java.util.Arrays;

public class GameBoard {
    private int[][] initial;
    private final int[][] player;
    private int[][] solution;

    public static final int GRID_SIZE = 9;

    private static final int sqrt = (int) Math.sqrt(GRID_SIZE);
    private static final int BOX_SUM = (GRID_SIZE * (GRID_SIZE + 1)) / 2;

    public GameBoard() {
        solution = null;
        initial = new int[][]
                {
                        {0, 0, 0, 4, 0, 0, 0, 9, 0},
                        {6, 0, 7, 0, 0, 0, 8, 0, 4},
                        {0, 1, 0, 7, 0, 9, 0, 0, 3},
                        {9, 0, 1, 0, 7, 0, 0, 3, 0},
                        {0, 0, 2, 0, 0, 0, 9, 0, 0},
                        {0, 5, 0, 0, 4, 0, 1, 0, 7},
                        {3, 0, 0, 5, 0, 2, 0, 7, 0},
                        {4, 0, 6, 0, 0, 0, 3, 0, 1},
                        {0, 7, 0, 0, 0, 4, 0, 0, 0}
                };

        player = new int[GRID_SIZE][GRID_SIZE];
    }


    public int[][] getInitial() {
        return initial;
    }

    public int[][] getPlayer() {
        return player;
    }

    public void modifyPlayer(int val, int row, int col) {
        if (initial[row][col] == 0) {
            if (val >= 0 && val <= GRID_SIZE) {
                player[row][col] = val;
            } else {
                System.out.println("Value passed to player falls out of range");
            }
        }
    }


    public boolean checkForSuccess() {
        int[][] combined = new int[GRID_SIZE][GRID_SIZE];
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                if (initial[row][col] != 0) {
                    combined[row][col] = initial[row][col];
                } else {
                    combined[row][col] = player[row][col];
                }
            }
        }
        return checkRows(combined) && checkColumns(combined) && checkBoxes(combined);
    }

    private boolean checkRows(int[][] board) {
        for (int row = 0; row < GRID_SIZE; row++) {
            int sum = Arrays.stream(board[row]).sum();
            if (sum != BOX_SUM) {
                return false;
            }
        }
        return true;
    }

    private boolean checkColumns(int[][] board) {
        for (int col = 0; col < GRID_SIZE; col++) {
            int sum = 0;
            for (int row = 0; row < GRID_SIZE; row++) {
                sum = sum + board[row][col];
            }
            if (sum != BOX_SUM) {
                return false;
            }
        }
        return true;
    }


    private boolean checkBoxes(int[][] board) {
        // O(n) => O(GRID_SIZE*GRID_SIZE) nothing crazy
        for (int rowOffset = 0; rowOffset < GRID_SIZE; rowOffset += sqrt) {
            for (int columnOffset = 0; columnOffset < GRID_SIZE; columnOffset += sqrt) {
                int sum = 0;
                for (int row = 0; row < sqrt; row++) {
                    for (int col = 0; col < sqrt; col++) {
                        sum = sum + board[row + rowOffset][col + columnOffset];
                    }
                }
                if (sum != BOX_SUM) {
                    return false;
                }
            }
        }
        return true;
    }


    private boolean isNumberInRow(int[][] board, int number, int row) {
        for (int i = 0; i < GRID_SIZE; i++) {
            if (board[row][i] == number) {
                return true;
            }
        }
        return false;
    }

    private boolean isNumberInColumn(int[][] board, int number, int column) {
        for (int i = 0; i < GRID_SIZE; i++) {
            if (board[i][column] == number) {
                return true;
            }
        }
        return false;
    }

    private boolean isNumberInBox(int[][] board, int number, int row, int column) {
        int localBoxRow = row - row % sqrt;
        int localBoxColumn = column - column % sqrt;
        for (int i = localBoxRow; i < localBoxRow + sqrt; i++) {
            for (int j = localBoxColumn; j < localBoxColumn + sqrt; j++) {
                if (board[i][j] == number) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isValidPlacement(int[][] board, int number, int row, int column) {
        return (!isNumberInRow(board, number, row)
                && !isNumberInColumn(board, number, column))
                && !isNumberInBox(board, number, row, column);
    }

    public boolean solveBoard() {
        if (solution == null) {
            solution = new int[GRID_SIZE][GRID_SIZE];
            for (int i = 0; i < GRID_SIZE; i++) {
                solution[i] = Arrays.copyOf(initial[i], initial[i].length);
            }
            return solveBoard(solution);
        } else {
            return true;
        }
    }

    private boolean solveBoard(int[][] board) {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int column = 0; column < GRID_SIZE; column++) {
                if (board[row][column] == 0) {
                    for (int numberToTry = 1; numberToTry <= GRID_SIZE; numberToTry++) {
                        if (isValidPlacement(board, numberToTry, row, column)) {
                            board[row][column] = numberToTry;
                            if (solveBoard(board)) {
                                return true;
                            } else {
                                board[row][column] = 0;
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    public int[][] getSolution() {
        return solution;
    }

    public void reset() {
        initial = getInitial(); // ideally should generate a new game-board
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                player[row][col] = 0;
            }
        }
        solution = null;
    }
}
