package org.eom.ttt_control;

import java.util.*;

public class GameBoard {

    // initialize variables
    private CellState[][] board = new CellState[3][3];
    private int dim;

    /**
     * Constructor: assuming no given board, will fill up the board
     * variable with EMPTY.
     *
     * @param dim the size of the game board
     */
    public GameBoard(int dim) {
        this.dim = dim;

        for (int row = 0; row < dim; row++) {
            for (int col = 0; col < dim; col++) {
                board[row][col] = CellState.EMPTY;
            }
        }
    }

    /**
     * Constructor: has a given board, will have the board variable
     * equal to the given board
     *
     * @param dim   the size of the game board
     * @param board a given board
     */
    public GameBoard(int dim, CellState[][] board) {
        this.dim = dim;

        for (int row = 0; row < dim; row++) {
            System.arraycopy(board[row], 0, this.board[row], 0, dim);
        }
    }

    /**
     * This method will return the opponent player of the given player
     *
     * @param player the given player
     * @return the opponent player
     */
    public static CellState switchPlayer(CellState player) {
        return (player == CellState.COMPUTER) ? CellState.HUMAN : CellState.COMPUTER;
    }

    /**
     * This method will returns the player on a specific cell on the board
     *
     * @param row    the row index of array
     * @param column the column index of array
     * @return the player on the given index cell
     */
    public CellState getCell(int row, int column) {
        return board[row][column];
    }

    /**
     * This method will add the given player into the specific cell given.
     *
     * @param row    the row index of array
     * @param col    the col index of array
     * @param player the player that will be put on the cell
     */
    public void setState(int row, int col, CellState player) {
        if (board[row][col] == CellState.EMPTY) {
            board[row][col] = player;
        }
    }

    /**
     * This method will loop through the board variable and add the index
     * of EMPTY cells into a list.
     *
     * @return the list of indices of EMPTY cells
     */
    public List<int[]> getEmptySquare() {
        // create a list that will contain all the empty squares
        List<int[]> empty = new ArrayList<>();

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

    /**
     * This method will add all possible win solution into a list. It will
     * then loop through the list and turn each win solution into a set, which
     * will merge all common element into a single element. By determining what
     * the single element is, the method will return the winner
     *
     * @return current game state
     */
    public GameState checkWin() {
        CellState[][] cloneBoard = board;
        List<CellState[]> lines = new ArrayList<>();

        // rows
        Collections.addAll(lines, cloneBoard);

        // columns
        CellState[][] column = new CellState[dim][dim];
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                column[i][j] = cloneBoard[j][i];
            }
        }

        Collections.addAll(lines, column);

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
            Set<CellState> setLine = new HashSet<>();
            Collections.addAll(setLine, line);

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

    /**
     * This method clones the same game board
     *
     * @return the cloned game board object
     */
    @Override
    public GameBoard clone() {
        return new GameBoard(dim, board);
    }
}
