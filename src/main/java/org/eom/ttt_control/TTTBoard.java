package org.eom.ttt_control;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TTTBoard {

	// create useful variables
	GameState[][] board = new GameState[3][3];
	private int dim;

	
	public TTTBoard(int dim) {
		this.dim = dim;
		
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				board[row][col] = GameState.EMPTY;
			}
		}
	}
	
	public TTTBoard(int dim, GameState[][] board) {
		this.dim = dim;
		this.board = board;
	}

	public GameState square(int row, int column) {
		return board[row][column];
	}

	public void addState(int row, int col, GameState player) {
		if (board[row][col] == GameState.EMPTY) {
			board[row][col] = player;
		}
	}
	
	public void removeState(int row, int col) {
		if (board[row][col] != GameState.EMPTY) {
			board[row][col] = GameState.EMPTY;
		}
	}

	public List<int[]> getEmptySquare() {
		// create a list that will contain all the empty squares
		List<int[]> empty = new ArrayList<int[]>();

		// find empty squares on the board and add it to the list
		for (int row = 0; row < board.length; row++) {
			for (int column = 0; column < board.length; column++) {
				if (board[row][column] == GameState.EMPTY) {
					empty.add(new int[] { row, column });
				}
			}
		}

		return empty;
	}

	public GameState checkWin() {
		GameState[][] cloneBoard = board;
		List<GameState[]> lines = new ArrayList<GameState[]>();

		// rows
		for (GameState[] row : cloneBoard) {
			lines.add(row);
		}

		// columns
		GameState[][] column = new GameState[dim][dim];
		for (int i = 0; i < dim; i++) {
			for (int j = 0; j < dim; j++) {
				column[i][j] = cloneBoard[j][i];
			}
		}

		for (GameState[] col : column) {
			lines.add(col);
		}

		// diagnose
		GameState[] diagOne = new GameState[dim];
		GameState[] diagTwo = new GameState[dim];

		for (int i = 0; i < dim; i++) {
			diagOne[i] = cloneBoard[i][i];
			diagTwo[i] = cloneBoard[i][dim - i - 1];
		}

		lines.add(diagOne);
		lines.add(diagTwo);

		// check every line for win situation
		for (GameState[] line : lines) {
			Set<GameState> setLine = new HashSet<GameState>();
			for (GameState number : line) {
				setLine.add(number);
			}

			if (setLine.size() == 1 && !setLine.contains(GameState.EMPTY)) {
				return line[0];
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

	public TTTBoard clone() {
		return new TTTBoard(dim, board);
	}

	public static GameState switchPlayer(GameState player) {
		if (player == GameState.COMPUTER) {
			return GameState.HUMAN;
		} else {
			return GameState.COMPUTER;
		}
	}

}
