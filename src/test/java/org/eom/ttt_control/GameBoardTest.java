package org.eom.ttt_control;

import org.junit.Test;

import static org.eom.ttt_control.CellState.*;
import static org.junit.Assert.*;

public class GameBoardTest {

    @Test
    public void testClone() {
        CellState[][] board = {{EMPTY, EMPTY, EMPTY}, {EMPTY, EMPTY, EMPTY}, {EMPTY, EMPTY, EMPTY}};
        GameBoard board1 = new GameBoard(3, board);
        GameBoard board2 = board1.clone();
        assertEquals(EMPTY, board1.getCell(1, 2));
        assertEquals(EMPTY, board2.getCell(1, 2));

        board1.setState(1, 2, COMPUTER);
        assertEquals(EMPTY, board2.getCell(1, 2));
    }

    @Test
    public void testSwitchPlayer() {
        CellState player = HUMAN;
        CellState otherPlayer = GameBoard.switchPlayer(player);
        assertEquals(COMPUTER, otherPlayer);
    }

    @Test
    public void testCheckWinHuman() {
        /*
        x x x
          o
        o

        Check if check win method recognize the win situation
        for human
         */
        CellState[][] board = {{HUMAN, HUMAN, HUMAN}, {EMPTY, COMPUTER, EMPTY}, {COMPUTER, EMPTY, EMPTY}};
        GameBoard board1 = new GameBoard(3, board);
        GameState state = board1.checkWin();
        assertEquals(GameState.HUMAN_WIN, state);
    }

    @Test
    public void testCheckWinComputer() {
        /*
        o o o
          x
        x

        Check if check win method recognize the win situation
        for computer
         */
        CellState[][] board = {{COMPUTER, COMPUTER, COMPUTER}, {EMPTY, HUMAN, EMPTY}, {HUMAN, EMPTY, EMPTY}};
        GameBoard board1 = new GameBoard(3, board);
        GameState state = board1.checkWin();
        assertEquals(GameState.COMPUTER_WIN, state);
    }

    @Test
    public void testCheckWinDraw() {
        /*
        x o x
        o o x
        x x o

        Check if check win method recognize this draw situation
         */
        CellState[][] board = {{HUMAN, COMPUTER, HUMAN}, {COMPUTER, COMPUTER, HUMAN}, {HUMAN, HUMAN, COMPUTER}};
        GameBoard board1 = new GameBoard(3, board);
        GameState state = board1.checkWin();
        assertEquals(GameState.DRAW, state);
    }

    @Test
    public void testCheckWinPlaying() {
        /*
        x   x
          o
        o

        Check if check win method recognize that the game is
        still playing
         */
        CellState[][] board = {{HUMAN, EMPTY, HUMAN}, {EMPTY, COMPUTER, EMPTY}, {COMPUTER, EMPTY, EMPTY}};
        GameBoard board1 = new GameBoard(3, board);
        GameState state = board1.checkWin();
        assertEquals(GameState.PLAYING, state);
    }

}