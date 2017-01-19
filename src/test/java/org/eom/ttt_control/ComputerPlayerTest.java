package org.eom.ttt_control;

import static org.junit.Assert.*;

import org.junit.Test;

public class ComputerPlayerTest {

    @Test
    public void testMinimaxWinRow() {
        /*
        x x o
          x x
        o   o

        Check if computer knows how to win the game with win case
        on a row of the board
         */
        CellState[][] board = {{CellState.HUMAN, CellState.HUMAN, CellState.COMPUTER},
                {CellState.EMPTY, CellState.HUMAN, CellState.HUMAN},
                {CellState.COMPUTER, CellState.EMPTY, CellState.COMPUTER}};
        ComputerPlayer obj = new ComputerPlayer(2);
        int[] result = obj.getMove(new GameBoard(3, board), CellState.COMPUTER);
        int row = result[1];
        int col = result[2];
        assertEquals("Bad Move X: " + row, 2, row);
        assertEquals("Bad Move Y: " + col, 1, col);
    }

    @Test
    public void testMinimaxWinCol() {
        /*
        x
        o o x
        x o x

        Check if computer knows how to win the game with win case
        on a column of the board
         */
        CellState[][] board = {{CellState.HUMAN, CellState.EMPTY, CellState.EMPTY},
                {CellState.COMPUTER, CellState.COMPUTER, CellState.HUMAN},
                {CellState.HUMAN, CellState.COMPUTER, CellState.HUMAN}};
        ComputerPlayer obj = new ComputerPlayer(2);
        int[] result = obj.getMove(new GameBoard(3, board), CellState.COMPUTER);
        int row = result[1];
        int col = result[2];
        assertEquals("Bad Move X: " + row, 0, row);
        assertEquals("Bad Move Y: " + col, 1, col);
    }

    @Test
    public void testMinimaxWinDiag() {
        /*
        x x
        o o x
        o

        Check if computer knows how to win the game with win case
        on a diagonal of the board
         */
        CellState[][] board = {{CellState.HUMAN, CellState.HUMAN, CellState.EMPTY},
                {CellState.COMPUTER, CellState.COMPUTER, CellState.HUMAN},
                {CellState.COMPUTER, CellState.EMPTY, CellState.EMPTY}};
        ComputerPlayer obj = new ComputerPlayer(2);
        int[] result = obj.getMove(new GameBoard(3, board), CellState.COMPUTER);
        int row = result[1];
        int col = result[2];
        assertEquals("Bad Move X: " + row, 0, row);
        assertEquals("Bad Move Y: " + col, 2, col);
    }

    @Test
    public void testMinimaxDefRow() {
        /*
        x x.
        x o
        o o x

        Check if computer knows how to defend to not lose the game
        with opponent win case on a row of the board
         */
        CellState[][] board = {{CellState.HUMAN, CellState.HUMAN, CellState.EMPTY},
                {CellState.HUMAN, CellState.COMPUTER, CellState.EMPTY},
                {CellState.COMPUTER, CellState.COMPUTER, CellState.HUMAN}};
        ComputerPlayer obj = new ComputerPlayer(2);
        int[] result = obj.getMove(new GameBoard(3, board), CellState.COMPUTER);
        int row = result[1];
        int col = result[2];
        assertEquals("Bad Move X: " + row, 0, row);
        assertEquals("Bad Move Y: " + col, 2, col);
    }

    @Test
    public void testMinimaxDefCol() {
        /*
        x o x
        x x o
            o

        Check if computer knows how to defend to not lose the game
        with opponent win case on a column of the board
         */
        CellState[][] board = {{CellState.HUMAN, CellState.COMPUTER, CellState.HUMAN},
                {CellState.HUMAN, CellState.HUMAN, CellState.COMPUTER},
                {CellState.EMPTY, CellState.EMPTY, CellState.COMPUTER}};
        ComputerPlayer obj = new ComputerPlayer(2);
        int[] result = obj.getMove(new GameBoard(3, board), CellState.COMPUTER);
        int row = result[1];
        int col = result[2];
        assertEquals("Bad Move X: " + row, 2, row);
        assertEquals("Bad Move Y: " + col, 0, col);
    }

    @Test
    public void testMinimaxDefDiag() {
        /*
        x o x
        x x o
        o

        Check if computer knows how to defend to not lose the game
        with opponent win case on a diagonal of the board
         */
        CellState[][] board = {{CellState.HUMAN, CellState.COMPUTER, CellState.HUMAN},
                {CellState.HUMAN, CellState.HUMAN, CellState.COMPUTER},
                {CellState.COMPUTER, CellState.EMPTY, CellState.EMPTY}};
        ComputerPlayer obj = new ComputerPlayer(2);
        int[] result = obj.getMove(new GameBoard(3, board), CellState.COMPUTER);
        int row = result[1];
        int col = result[2];
        assertEquals("Bad Move X: " + row, 2, row);
        assertEquals("Bad Move Y: " + col, 2, col);
    }
}
