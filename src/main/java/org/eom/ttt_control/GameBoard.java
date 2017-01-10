package org.eom.ttt_control;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GameBoard {

    private CellState[][] board = new CellState[3][3];
    private int dim;

    public GameBoard(int dim) {
        this.dim = dim;

        for (int row = 0; row < dim; row++) {
            for (int col = 0; col < dim; col++) {
                board[row][col] = CellState.EMPTY;
            }
        }
    }

    public GameBoard(int dim, CellState[][] board) {
        this.dim = dim;

        for (int row = 0; row < dim; row++) {
            for (int col = 0; col < dim; col++) {
                this.board[row][col] = board[row][col];
            }
        }
    }

    public CellState getCell(int row, int column) {
        return board[row][column];
    }

    public void setState(int row, int col, CellState player) {
        if (board[row][col] == CellState.EMPTY) {
            board[row][col] = player;
        }
    }

    public List<int[]> getEmptySquare() {
        // create a list that will contain all the empty squares
        List<int[]> empty = new ArrayList<int[]>();

        // find empty squares on the board and add it to the list
        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board.length; column++) {
                if (board[row][column] == CellState.EMPTY) {
                    empty.add(new int[]{row, column});
                }
            }
        }

        return empty;
    }

    public GameState checkWin() {
        CellState[][] cloneBoard = board;
        List<CellState[]> lines = new ArrayList<CellState[]>();

        // rows
        for (CellState[] row : cloneBoard) {
            lines.add(row);
        }

        // columns
        CellState[][] column = new CellState[dim][dim];
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                column[i][j] = cloneBoard[j][i];
            }
        }

        for (CellState[] col : column) {
            lines.add(col);
        }

        // diagnose
        CellState[] diagOne = new CellState[dim];
        CellState[] diagTwo = new CellState[dim];

        for (int i = 0; i < dim; i++) {
            diagOne[i] = cloneBoard[i][i];
            diagTwo[i] = cloneBoard[i][dim - i - 1];
        }

        lines.add(diagOne);
        lines.add(diagTwo);

        // check every line for win situation
        for (CellState[] line : lines) {
            Set<CellState> setLine = new HashSet<CellState>();
            for (CellState number : line) {
                setLine.add(number);
            }

            if (setLine.size() == 1 && !setLine.contains(CellState.EMPTY)) {
                return (line[0] == CellState.COMPUTER) ? GameState.COMPUTER_WIN : GameState.HUMAN_WIN;
            }
        }

        // no winner, check for draw
        List<int[]> empty = getEmptySquare();
        if (empty.size() == 0) {
            return GameState.DRAW;
        }

        // game still in progress
        return GameState.PLAYING;
    }

    @Override
    public GameBoard clone() {
        return new GameBoard(dim, board);
    }

    public static CellState switchPlayer(CellState player) {
        return (player == CellState.COMPUTER) ? CellState.HUMAN : CellState.COMPUTER;
    }

}
