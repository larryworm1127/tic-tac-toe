package org.eom.ttt_control;

import java.util.HashMap;
import java.util.Map;

public class ComputerPlayer {

    public Map<GameState, Integer> scores = new HashMap<>();

    public ComputerPlayer() {
        scores.put(GameState.COMPUTER_WIN, 1);
        scores.put(GameState.HUMAN_WIN, -1);
        scores.put(GameState.DRAW, 0);
    }

    public int[] miniMax(GameBoard board, CellState player) {
        return alphaBetaPruning(board, player, -2, 2);
    }

    public int[] alphaBetaPruning(GameBoard board, CellState player, int alpha, int beta) {
        CellState otherPlayer = GameBoard.switchPlayer(player);
        int bestScore = -2;
        int bestRow = -1;
        int bestCol = -1;

        if (board.checkWin() != GameState.PLAYING) {
            return new int[]{scores.get(board.checkWin()), bestRow, bestCol};
        }

        for (int[] move : board.getEmptySquare()) {
            GameBoard trial = board.clone();
            trial.setState(move[0], move[1], player);
            int score = alphaBetaPruning(trial, otherPlayer, -beta, -Integer.max(alpha, bestScore))[0];
            alpha = score * getScore(player);

            if (alpha == 1) {
                return new int[]{score, move[0], move[1]};
            } else if (alpha > bestScore) {
                bestScore = alpha;
                bestRow = move[0];
                bestCol = move[1];
            }

            if (bestScore >= beta) {
                break;
            }
        }

        return new int[]{bestScore * getScore(player), bestRow, bestCol};
    }

    public int getScore(CellState player) {
        // TODO: player might be EMPTY
        return scores.get((player == CellState.COMPUTER) ? GameState.COMPUTER_WIN : GameState.HUMAN_WIN);
    }
}
