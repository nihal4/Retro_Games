package com.example.retroboard;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

class TicTacToeActivity : AppCompatActivity() {
    private lateinit var gridLayout: GridLayout;
    private lateinit var restartButton: Button;
    private lateinit var player1ScoreTextView: TextView;
    private lateinit var player2ScoreTextView: TextView;
    private lateinit var gameResultTextView: TextView;
    private lateinit var roundTextView: TextView;
    private lateinit var cellButtons: Array<Button>;

    private var board = Array(3) { Array(3) { "" } };
    private var currentPlayer = "X";
    private var gameActive = true;
    private var player1Score = 0;
    private var player2Score = 0;
    private var player1GamesWon = 0;
    private var player2GamesWon = 0;
    private var round = 1;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tic_tac_toe);

        // Initialize UI elements
        gridLayout = findViewById(R.id.gridLayout);
        restartButton = findViewById(R.id.restartButton);
        player1ScoreTextView = findViewById(R.id.player1Score);
        player2ScoreTextView = findViewById(R.id.player2Score);
        gameResultTextView = findViewById(R.id.gameResult);
        roundTextView = findViewById(R.id.roundTextView);
        restartButton.visibility = View.VISIBLE;

        // Initialize cell buttons
        cellButtons = arrayOf(
            findViewById(R.id.cell_0),
            findViewById(R.id.cell_1),
            findViewById(R.id.cell_2),
            findViewById(R.id.cell_3),
            findViewById(R.id.cell_4),
            findViewById(R.id.cell_5),
            findViewById(R.id.cell_6),
            findViewById(R.id.cell_7),
            findViewById(R.id.cell_8)
        );

        // Set onClickListeners for the buttons
        for (i in cellButtons.indices) {
            cellButtons[i].setOnClickListener { v ->
                val row = i / 3;
                val col = i % 3;
                if (board[row][col].isEmpty() && gameActive) {
                    board[row][col] = currentPlayer;
                    (v as Button).text = currentPlayer;
                    checkWinner();
                    if (gameActive) currentPlayer = if (currentPlayer == "X") "O" else "X";
                }
            };
        }

        // Restart button logic
        restartButton.setOnClickListener {
            val animation = AnimationUtils.loadAnimation(this, R.anim.button_click);
            it.startAnimation(animation);
            resetGame();
        };
    }

    private fun checkWinner() {
        // Check rows and columns for a winner
        for (i in 0 until 3) {
            // Check rows
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][0].isNotEmpty()) {
                declareRoundWinner(board[i][0]);
                return;
            }
            // Check columns
            if (board[0][i] == board[1][i] && board[1][i] == board[2][i] && board[0][i].isNotEmpty()) {
                declareRoundWinner(board[0][i]);
                return;
            }
        }

        // Check diagonals
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0].isNotEmpty()) {
            declareRoundWinner(board[0][0]);
            return;
        }
        if (board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2].isNotEmpty()) {
            declareRoundWinner(board[0][2]);
            return;
        }

        // Check if the game is a draw
        if (board.all { row -> row.all { it.isNotEmpty() } }) {
            Toast.makeText(this, "It's a draw! Replay the round.", Toast.LENGTH_SHORT).show();
            resetBoard(); // Reset the board to replay the round
        }
    }

    private fun declareRoundWinner(winner: String) {
        gameActive = false;

        // Update scores
        if (winner == "X") {
            player1Score++;
            player1GamesWon++;
        } else {
            player2Score++;
            player2GamesWon++;
        }

        // Update score text views
        player1ScoreTextView.text = "Player 1: $player1Score";
        player2ScoreTextView.text = "Player 2: $player2Score";

        // Check for best-of-3 winner
        if (player1GamesWon == 2 || player2GamesWon == 2) {
            // Show winner screen
            setContentView(R.layout.winner_screen);
            val winnerMessage: TextView = findViewById(R.id.winnerMessage);
            winnerMessage.text = "Player $winner won!";
            winnerMessage.setBackgroundColor(Color.BLACK);
            winnerMessage.setTextColor(Color.WHITE);

            restartButton = findViewById(R.id.restartButton);
            restartButton.visibility = View.VISIBLE;
            restartButton.setOnClickListener {
                val animation = AnimationUtils.loadAnimation(this, R.anim.button_click);
                it.startAnimation(animation);
                resetGame();
            };
        } else {
            // Move to the next round if no overall winner yet
            round++;
            roundTextView.text = "Round $round";
            resetBoard();
        }
    }

    private fun resetBoard() {
        board = Array(3) { Array(3) { "" } };
        for (btn in cellButtons) {
            btn.text = "";
            btn.isEnabled = true;
        }
        currentPlayer = "X";
        gameActive = true;
    }

    private fun resetGame() {
        player1GamesWon = 0;
        player2GamesWon = 0;
        player1Score = 0;
        player2Score = 0;
        round = 1;

        setContentView(R.layout.activity_tic_tac_toe);
        recreate(); // Reinitialize activity
    }
}
