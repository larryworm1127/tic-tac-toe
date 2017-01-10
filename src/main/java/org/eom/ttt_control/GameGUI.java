package org.eom.ttt_control;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class GameGUI implements ActionListener {

    // GUI setup
    private JFrame frame;
    private JButton[][] buttons;
    private Color[] colour = {Color.WHITE, Color.BLUE, Color.RED, Color.BLACK};
    private JPanel gameBoard;
    private JLabel statusBar;

    // game board
    private int size;
    private CellState turn;

    // AI setup
    private CellState humanPlayer;
    private CellState aiPlayer;

    // other game control variables
    private GameBoard board;
    private ComputerPlayer computer;
    private boolean inProgress;

    public static void main(String[] args) {
        new GameGUI(3, CellState.COMPUTER);
    }

    // constructor
    public GameGUI(int dim, CellState aiPlayer) {
        // variables initialization
        this.size = dim;
        this.aiPlayer = aiPlayer;
        humanPlayer = GameBoard.switchPlayer(aiPlayer);

        // initialize status bar
        statusBar = new JLabel("         ");
        statusBar.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 14));
        statusBar.setBorder(BorderFactory.createEmptyBorder(2, 5, 4, 5));
        statusBar.setOpaque(true);
        statusBar.setBackground(Color.LIGHT_GRAY);

        // initialize frame
        frameInit();

        // set up a new game
        newGame();

        // set up game board
        boardSetUp();
    }

    public void boardSetUp() {
        // game board setup
        buttons = new JButton[size][size];
        gameBoard = new JPanel();
        gameBoard.setLayout(new GridLayout(size, size, 4, 4));
        gameBoard.setBackground(colour[3]);

        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                buttons[row][column] = new JButton();
                buttons[row][column].setSize(100, 100);
                buttons[row][column].setBackground(colour[0]);
                buttons[row][column].addActionListener(this);
                gameBoard.add(buttons[row][column]);
            }
        }

        // add game board onto frame
        frame.add(gameBoard);
    }

    public void frameInit() {
        frame = new JFrame("Tic Tac Toe");
        frame.setSize(size * 100, size * 100 + 30);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void newGame() {
        board = new GameBoard(size);
        computer = new ComputerPlayer();
        inProgress = true;
        turn = CellState.HUMAN;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (event.getSource() == buttons[row][col]) {
                    humanMove(row, col);
                }
            }
        }
    }

    public void humanMove(int row, int col) {
        if (inProgress && turn == humanPlayer) {
            if (board.getCell(row, col) == CellState.EMPTY) {
                board.setState(row, col, humanPlayer);
                buttons[row][col].setBackground(colour[1]);
                buttons[row][col].setEnabled(false);
                turn = aiPlayer;

                GameState winner = board.checkWin();
                if (winner != GameState.PLAYING) {
                    gameOver(winner);
                }

                aiMove();
            }
        }
    }

    public void aiMove() {
        if (inProgress && turn == aiPlayer) {
            int[] result = computer.miniMax(board, aiPlayer);
            int[] move = new int[]{result[1], result[2]};
            if (board.getCell(move[0], move[1]) == CellState.EMPTY) {
                board.setState(move[0], move[1], aiPlayer);
                buttons[move[0]][move[1]].setBackground(colour[2]);
                buttons[move[0]][move[1]].setEnabled(false);
                turn = humanPlayer;

                GameState winner = board.checkWin();
                if (winner != GameState.PLAYING) {
                    gameOver(winner);
                }
            }
        }
    }

    public void gameOver(GameState winner) {
        if (winner == GameState.HUMAN_WIN) {
            JOptionPane.showMessageDialog(gameBoard, "Human wins!");
        } else if (winner == GameState.COMPUTER_WIN) {
            JOptionPane.showMessageDialog(gameBoard, "Computer wins!");
        } else if (winner == GameState.DRAW) {
            JOptionPane.showMessageDialog(gameBoard, "It is a tie!");
        }

        inProgress = false;

        int choice = JOptionPane.showOptionDialog(gameBoard, "Game Over, Would You Like To Play Again?", "GameOver",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                new String[]{"Play Again", "Exit"}, 1);

        if (choice == 0) {
            frame.dispose();
            new GameGUI(size, aiPlayer);
        } else {
            System.exit(1);
        }
    }
}
