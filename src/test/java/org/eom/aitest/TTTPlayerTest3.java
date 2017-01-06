package org.eom.aitest;

import static org.junit.Assert.*;

import org.eom.ttt_control.GameState;
import org.eom.ttt_control.TTTBoard;
import org.eom.ttt_control.TTTPlayer;
import org.junit.Test;

public class TTTPlayerTest3 {

	@Test
	public void testmmMove() {
		GameState[][] board = {{GameState.HUMAN, GameState.HUMAN, GameState.EMPTY}, 
				{GameState.COMPUTER, GameState.COMPUTER, GameState.HUMAN}, 
				{GameState.COMPUTER, GameState.EMPTY, GameState.EMPTY}};
		TTTPlayer obj = new TTTPlayer();
		int[] result = obj.miniMax(new TTTBoard(3, board), GameState.COMPUTER);
		int row = result[1];
		int col = result[2];
		System.out.println(row);
		System.out.println(col);
		assertEquals("Bad Move X: " + row, 0, row);
		assertEquals("Bad Move Y: " + col, 2, col);
	}

}
