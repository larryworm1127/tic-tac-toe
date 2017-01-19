package org.eom.ttt_control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ComputerPlayer {

    // initialize variables
    private Map<GameState, Integer> scores = new HashMap<>();
    private int difficulty;

    /**
     * Constructor
     *
     * @param difficulty the difficulty of computer player
     */
    public ComputerPlayer(int difficulty) {
        this.difficulty = difficulty;

        scores.put(GameState.COMPUTER_WIN, 1);
        scores.put(GameState.HUMAN_WIN, -1);
        scores.put(GameState.DRAW, 0);
    }

    /**
     * This method calls on corresponding methods depending
     * on the difficulty of the computer player
     *
     * @param board  the game board object
     * @param player the player that have to move
     * @return an array with two elements representing the move
     */
    public int[] getMove(GameBoard board, CellState player) {
        if (difficulty == 0) {
            return randomMoveGenerator(board);
        } else {
            return alphaBetaPruning(board, player, -2, 2);
        }
    }

    /**
     * This method sets the difficulty of the computer player
     *
     * @param difficulty the new difficulty
     */
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * This method uses alpha-beta-pruning (minimax) to search for
     * all possible opponent moves with a given board. It will create a
     * clone game board object and simulate all the moves on the clone board.
     * The moves are then scored and the highest score move is chose to be returned
     * (Medium and Hard level)
     *
     * @param board  the game board object
     * @param player the player that have to move
     * @param alpha  maximum lower bound of possible solutions
     * @param beta   minimum upper bound of possible solutions
     * @return an array with two elements representing the move
     */
    private int[] alphaBetaPruning(GameBoard board, CellState player, int alpha, int beta) {
        CellState otherPlayer = GameBoard.switchPlayer(player);
        int bestScore = -2;
        int bestRow = -1;
        int bestCol = -1;

        if (board.checkWin() != GameState.PLAYING) {
            return new int[]{scores.get(board.checkWin()), bestRow, bestCol};
        }

        List<int[]> emptySquares = board.getEmptySquare();
        int depth = 0;
        if (difficulty == 1) {
            depth = emptySquares.size() / 2;
        } else if (difficulty == 2) {
            depth = emptySquares.size();
        }

        for (int i = 0; i < depth; i++) {
            int[] move = emptySquares.get(i);
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

    /**
     * This method find all the possible move for computer player
     * and returns a random one as computer move. (Easy level)
     *
     * @param board the game board object
     * @return an array with two elements representing the move
     */
    private int[] randomMoveGenerator(GameBoard board) {
        Random random = new Random();
        List<int[]> emptySquares = board.getEmptySquare();
        int index = random.nextInt(emptySquares.size() - 1);
        return new int[]{0, emptySquares.get(index)[0], emptySquares.get(index)[1]};
    }

    /**
     * This method returns a score from score map with given player
     *
     * @param player the player that has to get score
     * @return the corresponding score from score map.
     */
    private int getScore(CellState player) {
        return scores.get((player == CellState.COMPUTER) ? GameState.COMPUTER_WIN : GameState.HUMAN_WIN);
    }


}
