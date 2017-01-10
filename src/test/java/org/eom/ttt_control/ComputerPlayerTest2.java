package org.eom.ttt_control;

import static org.junit.Assert.*;

import org.junit.Test;

public class ComputerPlayerTest2 {

	@Test
	public void testMinimax() {
		CellState[][] board = {{CellState.HUMAN, CellState.COMPUTER, CellState.HUMAN},
				{CellState.HUMAN, CellState.HUMAN, CellState.COMPUTER},
				{CellState.EMPTY, CellState.EMPTY, CellState.COMPUTER}};
		ComputerPlayer obj = new ComputerPlayer();
		int[] result = obj.miniMax(new GameBoard(3, board), CellState.COMPUTER);
		int row = result[1];
		int col = result[2];
		assertEquals("Bad Move X: " + row, 2, row);
		assertEquals("Bad Move Y: " + col, 0, col);
	}

}
