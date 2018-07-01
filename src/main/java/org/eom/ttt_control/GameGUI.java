package org.eom.ttt_control;

import org.eom.main.MainControl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameGUI implements ActionListener {

    // GUI setup
    private JFrame frame;
    private JButton[][] buttons;
    private JPanel gameBoard;
    private JPanel mainPanel;
    private JLabel statusBar;

    private JMenuItem setDiff;
    private JMenuItem switchUser;
    private JMenuItem computerWins;
    private JMenuItem humanWins;
    private JMenuItem draws;
    private JMenuItem instruction;

    // game board
    private int size;

    // AI setup
    private CellState humanPlayer;
    private CellState aiPlayer;

    // other game control variables
    private GameBoard board;
    private ComputerPlayer computer;
    private GameState currentState;
    private CellState currentPlayer;
    private int difficulty = 2;
    private int numWinComputer = 0;
    private int numWinHuman = 0;
    private int numDraw = 0;

    /**
     * Constructor that calls gameSetup method
     *
     * @param dim      the size of the game board
     * @param aiPlayer the computer player
     */
    public GameGUI(int dim, CellState aiPlayer) {
        gameSetup(dim, aiPlayer);
    }

    /**
     * The method initialize variables and calls
     * other init methods
     *
     * @param dim      the size of the game board
     * @param aiPlayer the computer player
     */
    private void gameSetup(int dim, CellState aiPlayer) {
        // variables initialization
        this.size = dim;
        this.aiPlayer = aiPlayer;
        humanPlayer = GameBoard.switchPlayer(aiPlayer);

        // initialize frame
        guiInit();

        // set up a new game
        newGame();

        // set up game board
        boardSetUp();
    }

    /**
     * The method initialize and register all
     * GUI component of the game.
     */
    private void guiInit() {
        // initialize frame
        frame = new JFrame("Tic Tac Toe");
        frame.setSize(size * 100, size * 100 + 60);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // initialize main panel
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(3, 3));
        frame.add(mainPanel);

        // initialize status bar
        statusBar = new JLabel("         ");
        statusBar.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 14));
        statusBar.setBorder(BorderFactory.createEmptyBorder(2, 5, 4, 5));
        statusBar.setOpaque(true);
        statusBar.setBackground(Color.LIGHT_GRAY);
        frame.add(statusBar, BorderLayout.PAGE_END);

        // initialize menu bar
        JMenuBar menuBar = new JMenuBar();
        frame.add(menuBar, BorderLayout.PAGE_START);

        JMenu setting = new JMenu("Setting");
        menuBar.add(setting);

        setDiff = new JMenuItem("Set Difficulty");
        setDiff.addActionListener(this);
        setting.add(setDiff);

        switchUser = new JMenuItem("Switch User");
        switchUser.addActionListener(this);
        setting.add(switchUser);

        instruction = new JMenuItem("Instruction");
        instruction.addActionListener(this);
        setting.add(instruction);

        JMenu stats = new JMenu("Stats");
        menuBar.add(stats);

        computerWins = new JMenuItem("Computer Wins: " + numWinComputer);
        stats.add(computerWins);

        humanWins = new JMenuItem("Human Wins: " + numWinHuman);
        stats.add(humanWins);

        draws = new JMenuItem("Draws: " + numDraw);
        stats.add(draws);
    }

    /**
     * This method initialize the game board
     * panel and add all the buttons into the
     * panel.
     */
    private void boardSetUp() {
        // game board setup
        buttons = new JButton[size][size];
        gameBoard = new JPanel();
        gameBoard.setLayout(new GridLayout(size, size, 4, 4));
        gameBoard.setBackground(Color.black);

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                buttons[row][col] = new JButton();
                buttons[row][col].setSize(100, 100);
                buttons[row][col].addActionListener(this);
                buttons[row][col].setBackground(Color.white);
                gameBoard.add(buttons[row][col]);
            }
        }

        // add game board onto frame
        mainPanel.add(gameBoard);
    }

    /**
     * This method initialize all the game
     * control variables and objects
     */
    private void newGame() {
        board = new GameBoard(size);
        computer = new ComputerPlayer(difficulty);
        currentPlayer = CellState.HUMAN;
        currentState = GameState.PLAYING;
    }

    /**
     * This method receive the location of the button
     * clicked by human player and update the color of
     * the button. It also update the state of the game
     * and calls aiPlayer.
     *
     * @param row the row index of button clicked
     * @param col the column index of button clicked
     */
    private void humanMove(int row, int col) {
        if (currentState.equals(GameState.PLAYING) && currentPlayer == humanPlayer && board.getCell(row, col).equals(CellState.EMPTY)) {
            board.setState(row, col, humanPlayer);
            buttons[row][col].setBackground(Color.blue);
            buttons[row][col].setEnabled(false);
            currentPlayer = aiPlayer;

            GameState winner = board.checkWin();
            if (winner != GameState.PLAYING) {
                gameOver(winner);
            } else {
                updateStatusBar();
            }

            aiMove();
        }
    }


    /**
     * This method calls getMove() method from ComputerPlayer
     * class. It will receive the move it should make and updates
     * the color of the button. It also update the state of the game
     */
    private void aiMove() {
        if (currentState.equals(GameState.PLAYING) && currentPlayer == aiPlayer) {
            int[] result = computer.getMove(board, aiPlayer);
            int[] move = new int[]{result[1], result[2]};
            if (board.getCell(move[0], move[1]) == CellState.EMPTY) {
                board.setState(move[0], move[1], aiPlayer);
                buttons[move[0]][move[1]].setBackground(Color.red);
                buttons[move[0]][move[1]].setEnabled(false);
                currentPlayer = humanPlayer;

                GameState winner = board.checkWin();
                if (winner != GameState.PLAYING) {
                    gameOver(winner);
                } else {
                    updateStatusBar();
                }
            }
        }
    }

    /**
     * This method will end the game and do necessary
     * actions depending on the game state.
     *
     * @param winner a end game GameState
     */
    private void gameOver(GameState winner) {
        currentState = winner;
        updateStatusBar();

        mainPanel.remove(gameBoard);
        JButton playAgain = new JButton("Play Again!");
        playAgain.addActionListener(e -> {
            frame.dispose();
            gameSetup(size, aiPlayer);
        });

        Font font = new Font("Monospaced", Font.BOLD, 20);
        playAgain.setFont(font);
        playAgain.setOpaque(false);
        playAgain.setContentAreaFilled(false);
        playAgain.setBorderPainted(false);

        mainPanel.add(playAgain, BorderLayout.CENTER, 0);

        if (winner == GameState.COMPUTER_WIN) {
            numWinComputer++;
            computerWins.setText("Computer: " + numWinComputer);
        } else if (winner == GameState.HUMAN_WIN) {
            numWinHuman++;
            humanWins.setText("Human: " + numWinHuman);
        } else if (winner == GameState.DRAW) {
            numDraw++;
            draws.setText("Draws: " + numDraw);
        }
    }

    /**
     * This method update the status bar (JLabel) on the
     * bottom of the frame
     */
    private void updateStatusBar() {
        if (currentState == GameState.PLAYING) {
            statusBar.setForeground(Color.black);
            if (currentPlayer == CellState.COMPUTER) {
                statusBar.setText("Computer Player's Turn");
            } else {
                statusBar.setText("Human Player's Turn");
            }
        } else if (currentState == GameState.COMPUTER_WIN) {
            statusBar.setForeground(Color.red);
            statusBar.setText("Computer Win! Click to play again.");
        } else if (currentState == GameState.HUMAN_WIN) {
            statusBar.setForeground(Color.red);
            statusBar.setText("Human Win! Click to play again.");
        } else if (currentState == GameState.DRAW) {
            statusBar.setForeground(Color.red);
            statusBar.setText("It's a Draw! Click to play again");
        }
    }

    /**
     * This method is an event handler for setDiff
     * menuItem. It sets the difficulty of the computer
     * player.
     */
    private void setDifficulty() {
        int choice = JOptionPane.showOptionDialog(frame, "Select Difficulty", "Set Difficulty",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                new String[]{"Easy", "Medium", "Hard"}, 1);

        if (choice == 0) {
            difficulty = 0;
            frame.dispose();
            gameSetup(size, aiPlayer);
            computer.setDifficulty(difficulty);
        } else if (choice == 1) {
            difficulty = 1;
            frame.dispose();
            gameSetup(size, aiPlayer);
            computer.setDifficulty(difficulty);
        } else if (choice == 2) {
            difficulty = 2;
            frame.dispose();
            gameSetup(size, aiPlayer);
            computer.setDifficulty(difficulty);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (e.getSource() == buttons[i][j]) {
                    humanMove(i, j);
                }
            }
        }

        if (e.getSource() == setDiff) {
            setDifficulty();
        } else if (e.getSource() == switchUser) {
            frame.dispose();
            MainControl.login();
        } else if (e.getSource() == instruction) {
            JOptionPane.showMessageDialog(frame, "Tic-tac-toe (also known as noughts and crosses or Xs and Os) is a" +
                    "game for two players, X and O, who take turns marking the spaces in a 3×3 grid.\nThe player who succeeds " +
                    "in placing three of their marks in a horizontal, vertical, or diagonal row wins the game.", "Instruction", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
