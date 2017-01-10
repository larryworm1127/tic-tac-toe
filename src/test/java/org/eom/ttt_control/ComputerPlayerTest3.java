package org.eom.ttt_control;

import static org.junit.Assert.*;

import org.junit.Test;

public class ComputerPlayerTest3 {

	@Test
	public void testMinimax() {
		CellState[][] board = {{CellState.HUMAN, CellState.HUMAN, CellState.EMPTY},
				{CellState.COMPUTER, CellState.COMPUTER, CellState.HUMAN},
				{CellState.COMPUTER, CellState.EMPTY, CellState.EMPTY}};
		ComputerPlayer obj = new ComputerPlayer();
		int[] result = obj.miniMax(new GameBoard(3, board), CellState.COMPUTER);
		int row = result[1];
		int col = result[2];
		assertEquals("Bad Move X: " + row, 0, row);
		assertEquals("Bad Move Y: " + col, 2, col);
	}

}
