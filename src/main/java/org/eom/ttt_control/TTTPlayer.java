package org.eom.ttt_control;

import java.util.HashMap;
import java.util.Map;

public class TTTPlayer {

	public Map<GameState, Integer> scores = new HashMap<>();

	public TTTPlayer() {
		scores.put(GameState.COMPUTER, 1);
		scores.put(GameState.HUMAN, -1);
		scores.put(GameState.DRAW, 0);
	}

	public int[] miniMax(TTTBoard board, GameState player) {
		return alphaBetaPruning(board, player, -2, 2);
	}

	public int[] alphaBetaPruning(TTTBoard board, GameState player, int alpha, int beta) {
		GameState otherPlayer = TTTBoard.switchPlayer(player);
		int bestScore = -2;
		int bestRow = -1;
		int bestCol = -1;

		if (board.checkWin() != GameState.PLAYING) {
			return new int[] { scores.get(board.checkWin()), bestRow, bestCol };
		}

		for (int[] move : board.getEmptySquare()) {
			TTTBoard trial = board.clone();
			trial.addState(move[0], move[1], player);
			int score = alphaBetaPruning(trial, otherPlayer, -beta, -Integer.max(alpha, bestScore))[0];
			alpha = score * scores.get(player);
			trial.removeState(move[0], move[1]);

			if (alpha == 1) {
				return new int[] { score, move[0], move[1] };
			} else if (alpha > bestScore) {
				bestScore = alpha;
				bestRow = move[0];
				bestCol = move[1];
			}
			
			if (bestScore >= beta) {
				break;
			}
		}

		return new int[] { bestScore * scores.get(player), bestRow, bestCol };
	}
}
