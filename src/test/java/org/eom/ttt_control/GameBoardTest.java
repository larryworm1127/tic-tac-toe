package org.eom.ttt_control;

import org.junit.Test;

import static org.eom.ttt_control.CellState.*;
import static org.junit.Assert.*;

public class GameBoardTest {

    @Test
    public void testClone() throws Exception {
        GameBoard board1 = new GameBoard(3, new CellState[][]{{EMPTY, EMPTY, EMPTY}, {EMPTY, EMPTY, EMPTY}, {EMPTY, EMPTY, EMPTY}});
        GameBoard board2 = board1.clone();
        assertEquals(EMPTY, board1.getCell(1, 2));
        assertEquals(EMPTY, board2.getCell(1, 2));

        board1.setState(1, 2, COMPUTER);
        assertEquals(EMPTY, board2.getCell(1, 2));
    }

}