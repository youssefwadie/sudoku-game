package com.github.youssefwadie.sudokugame;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.ResourceBundle;

public class SudokuController implements Initializable {
    @FXML
    private Canvas canvas;
    private final int boxSize = 50;

    private int playerSelectedRow;
    private int playerSelectedCol;

    private GameBoard gameboard;

    private boolean solvedManually;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gameboard = new GameBoard();
        playerSelectedCol = 0;
        playerSelectedRow = 0;
        solvedManually = true;
        GraphicsContext context = canvas.getGraphicsContext2D();
        drawOnCanvas(context);
    }

    public void canvasMouseClicked() {
        canvas.setOnMouseClicked(event -> {
            int mouseX = (int) event.getX();
            int mouseY = (int) event.getY();

            playerSelectedRow = mouseY / boxSize;
            playerSelectedCol = mouseX / boxSize;

            drawOnCanvas(canvas.getGraphicsContext2D());
        });
    }

    public void drawOnCanvas(GraphicsContext context) {
        if (!solvedManually) {
            drawEmptyBoard(context);
            drawInitialBoard(context);
            int[][] playerGrid = gameboard.getPlayer();
            int[][] initialGrid = gameboard.getInitial();
            int[][] solution = gameboard.getSolution();

            for (int row = 0; row < GameBoard.GRID_SIZE; row++) {
                for (int col = 0; col < GameBoard.GRID_SIZE; col++) {
                    int positionY = row * 50 + 30;
                    int positionX = col * 50 + 20;
                    context.setFont(new Font(20));

                    if (initialGrid[row][col] != 0) {
                        context.setFill(Color.BLACK);
                    } else if (initialGrid[row][col] == 0 && (playerGrid[row][col] == solution[row][col])) {
                        context.setFill(Color.GREEN);
                    } else {
                        context.setFill(Color.RED);
                    }
                    context.fillText(String.valueOf(solution[row][col]), positionX, positionY);
                }
            }
            return;
        }

        if (gameboard.checkForSuccess()) {
            context.clearRect(0, 0, 450, 450);
            context.setFill(Color.GREEN);
            context.setFont(new Font(36));
            context.fillText("SUCCESS!", 150, 250);
        } else {
            drawEmptyBoard(context);
            drawInitialBoard(context);
            drawPlayerEnteredNumbers(context);
        }

    }

    private void drawEmptyBoard(GraphicsContext context) {
        context.clearRect(0, 0, 450, 450);
        context.setStroke(Color.RED);
        context.setLineWidth(5);
        // draw a strokeRoundRect using the same technique we used for drawing our board.
        int boxMargin = 2;
        context.strokeRoundRect(playerSelectedCol * boxSize + boxMargin,
                playerSelectedRow * boxSize + boxMargin,
                46, 46,
                10, 10);

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                int positionY = row * boxSize + boxMargin;
                int positionX = col * boxSize + boxMargin;

                context.setFill(Color.WHITE);

                // account for the 2 * box margin ==> total of blank space
                int width = boxSize - boxMargin * 2;
                context.fillRoundRect(positionX, positionY, width, width, 10, 10);
            }
        }

    }

    private void drawInitialBoard(GraphicsContext context) {
        int[][] initial = gameboard.getInitial();
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                int positionY = row * boxSize + 30;
                int positionX = col * boxSize + 20;
                context.setFill(Color.BLACK);
                context.setFont(new Font(20));
                if (initial[row][col] != 0) {
                    // draw the number using the fillText method
                    context.fillText(String.valueOf(initial[row][col]), positionX, positionY);
                }
            }
        }
    }

    private void drawPlayerEnteredNumbers(GraphicsContext context) {
        // draw the players numbers from our GameBoard instance
        int[][] player = gameboard.getPlayer();
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                int positionY = row * 50 + 30;
                int positionX = col * 50 + 20;
                context.setFont(new Font(20));
                context.setFill(Color.DARKVIOLET);
                if (player[row][col] != 0) {
                    context.fillText(String.valueOf(player[row][col]), positionX, positionY);
                }
            }
        }
    }

    public void solve() {
        solvedManually = false;
        boolean solved = gameboard.solveBoard();
        drawOnCanvas(canvas.getGraphicsContext2D());
    }

    public void reset() {
        solvedManually = true;
        gameboard.reset();
        drawOnCanvas(canvas.getGraphicsContext2D());
    }

    public void buttonOnePressed() {
        gameboard.modifyPlayer(1, playerSelectedRow, playerSelectedCol);
        drawOnCanvas(canvas.getGraphicsContext2D());
    }

    public void buttonTwoPressed() {
        gameboard.modifyPlayer(2, playerSelectedRow, playerSelectedCol);
        drawOnCanvas(canvas.getGraphicsContext2D());
    }

    public void buttonThreePressed() {
        gameboard.modifyPlayer(3, playerSelectedRow, playerSelectedCol);
        drawOnCanvas(canvas.getGraphicsContext2D());
    }

    public void buttonFourPressed() {
        gameboard.modifyPlayer(4, playerSelectedRow, playerSelectedCol);
        drawOnCanvas(canvas.getGraphicsContext2D());
    }

    public void buttonFivePressed() {

        gameboard.modifyPlayer(5, playerSelectedRow, playerSelectedCol);
        drawOnCanvas(canvas.getGraphicsContext2D());
    }

    public void buttonSixPressed() {
        gameboard.modifyPlayer(6, playerSelectedRow, playerSelectedCol);
        drawOnCanvas(canvas.getGraphicsContext2D());

    }

    public void buttonSevenPressed() {
        gameboard.modifyPlayer(7, playerSelectedRow, playerSelectedCol);
        drawOnCanvas(canvas.getGraphicsContext2D());
    }

    public void buttonEightPressed() {
        gameboard.modifyPlayer(8, playerSelectedRow, playerSelectedCol);
        drawOnCanvas(canvas.getGraphicsContext2D());
    }

    public void buttonNinePressed() {
        gameboard.modifyPlayer(9, playerSelectedRow, playerSelectedCol);
        drawOnCanvas(canvas.getGraphicsContext2D());
    }

}