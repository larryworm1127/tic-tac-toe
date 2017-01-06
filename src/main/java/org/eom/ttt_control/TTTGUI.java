package org.eom.ttt_control;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class TTTGUI implements ActionListener {

	// GUI setup
	private JFrame frame;
	private JButton[][] boardButton;
	private Color[] colour = { Color.WHITE, Color.BLUE, Color.RED, Color.BLACK };
	private JPanel gameBoard;

	// game board
	private int size;
	private GameState turn;

	// AI setup
	public static GameState humanPlayer;
	public static GameState aiPlayer;

	// other game control variables
	private TTTBoard board;
	private TTTPlayer player;
	private boolean inProgress;
	private boolean wait;

	public static void main(String[] args) {
		new TTTGUI(3, GameState.COMPUTER);
	}

	// constructor
	public TTTGUI(int dim, GameState aiPlayer) {
		// variables initialization
		this.size = dim;
		TTTGUI.aiPlayer = aiPlayer;
		humanPlayer = TTTBoard.switchPlayer(aiPlayer);

		// initialize frame
		frameInit();

		// set up a new game
		newGame();

		// set up game board
		boardSetUp();
	}

	public void boardSetUp() {
		// game board setup
		boardButton = new JButton[size][size];
		gameBoard = new JPanel();
		gameBoard.setLayout(new GridLayout(size, size, 4, 4));
		gameBoard.setBackground(colour[3]);

		for (int row = 0; row < size; row++) {
			for (int column = 0; column < size; column++) {
				boardButton[row][column] = new JButton();
				boardButton[row][column].setSize(100, 100);
				boardButton[row][column].setBackground(colour[0]);
				boardButton[row][column].addActionListener(this);
				gameBoard.add(boardButton[row][column]);
			}
		}

		// add game board onto frame
		frame.add(gameBoard);
	}
	
	public void frameInit() {
		frame = new JFrame("Tic Tac Toe");
		frame.setSize(size * 100, size * 100 + 30);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public void newGame() {
		board = new TTTBoard(size);
		player = new TTTPlayer();
		inProgress = true;
		wait = false;
		turn = GameState.COMPUTER;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		for (int row = 0; row < size; row++) {
			for (int column = 0; column < size; column++) {
				if (event.getSource() == boardButton[row][column]) {
					humanMove(row, column);
				}
			}
		}
	}

	public void humanMove(int row, int column) {
		if (inProgress && turn == humanPlayer) {
			if (board.square(row, column) == GameState.EMPTY) {
				board.addState(row, column, humanPlayer);
				turn = aiPlayer;
				
				updateGUI();
				
				GameState winner = board.checkWin();
				if (winner != null) {
					gameOver(winner);
				}
				
				wait = true;
			}
		}
	}
	
	public void aiMove() {
		/*
		if (inProgress && turn == aiPlayer) {
			int[] move = (int[]) player.mmMove(board, aiPlayer).get(1);
			if (board.square(move[0], move[1]) == GameState.EMPTY) {
				board.move(move[0], move[1], aiPlayer);
				turn = humanPlayer;
				
				updateGUI();
				
				GameState winner = board.checkWin();
				if (winner != null) {
					gameOver(winner);
				}
			}
		}*/
	}

	public void gameOver(GameState winner) {
		if (winner == GameState.COMPUTER) {
			JOptionPane.showMessageDialog(gameBoard, "Player Red wins!");
		} else if (winner == GameState.COMPUTER) {
			JOptionPane.showMessageDialog(gameBoard, "Player Blue wins!");
		} else if (winner == GameState.DRAW) {
			JOptionPane.showMessageDialog(gameBoard, "It is a tie!");
		}

		inProgress = false;

		int choice = JOptionPane.showOptionDialog(gameBoard, "Game Over, Would You Like To Play Again?", "GameOver",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null,
				new String[] { "Play Again", "Exit" }, 1);

		if (choice == 0) {
			new TTTGUI(size, aiPlayer);
		} else {
			System.exit(1);
		}
	}

	public void updateGUI() {
		for (int row = 0; row < size; row++) {
			for (int column = 0; column < size; column++) {
				GameState symbol = board.square(row, column);
				System.out.print(symbol + " ");
				if (symbol == GameState.HUMAN) {
					boardButton[row][column].setBackground(colour[1]);
					boardButton[row][column].setEnabled(false);
				} else if (symbol == GameState.COMPUTER) {
					boardButton[row][column].setBackground(colour[2]);
					boardButton[row][column].setEnabled(false);
				}
			}
		}
		
		if (wait) {
			aiMove();
		} else {
			wait = true;
		}
		System.out.println("\n");
	}
}
