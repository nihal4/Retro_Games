package com.example.retroboard

import android.os.Bundle
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MemoryGameActivity : AppCompatActivity() {

    private lateinit var playerScoreView: TextView
    private lateinit var gridLayout: GridLayout
    private lateinit var restartButton: Button

    private val cards = mutableListOf(1, 2, 3, 4, 5, 6, 7, 8, 1, 2, 3, 4, 5, 6, 7, 8) // 8 pairs
    private val cardButtons = mutableListOf<Button>()

    private var firstCardIndex: Int? = null
    private var secondCardIndex: Int? = null
    private var playerScore = 0
    private var matchedPairs = 0
    private var isCheckingMatch = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memory_game)

        // Initialize UI components
        playerScoreView = findViewById(R.id.playerScore)
        gridLayout = findViewById(R.id.gridLayout)
        restartButton = findViewById(R.id.restartButton)

        // Initialize card buttons by ID
        for (i in 0 until 16) {
            val cardButton = findViewById<Button>(resources.getIdentifier("card_$i", "id", packageName))
            cardButton.setOnClickListener { flipCard(i) }
            cardButtons.add(cardButton)
        }

        // Initialize the game
        initializeGame()

        // Restart button listener
        restartButton.setOnClickListener {
            restartGame()
        }
    }

    private fun initializeGame() {
        // Shuffle cards
        cards.shuffle()

        // Set up cards dynamically
        for (i in cards.indices) {
            cardButtons[i].text = "" // Clear text
            cardButtons[i].isEnabled = true
        }

        // Reset game state
        resetGameState()
    }

    private fun flipCard(index: Int) {
        if (isCheckingMatch || index == firstCardIndex || cardButtons[index].text.isNotEmpty()) return

        val button = cardButtons[index]
        button.text = cards[index].toString()
        button.isEnabled = false

        if (firstCardIndex == null) {
            firstCardIndex = index
        } else {
            secondCardIndex = index
            isCheckingMatch = true
            setAllButtonsEnabled(false)

            // Check for match after a short delay
            gridLayout.postDelayed({ checkMatch() }, 1000)
        }
    }

    private fun checkMatch() {
        val firstIndex = firstCardIndex
        val secondIndex = secondCardIndex

        if (firstIndex != null && secondIndex != null) {
            val firstButton = cardButtons[firstIndex]
            val secondButton = cardButtons[secondIndex]

            // Increment the player's score regardless of match
            playerScore++
            updateScores()

            if (cards[firstIndex] == cards[secondIndex]) {
                // Match found
                matchedPairs++

                // Keep matched cards disabled
                firstButton.isEnabled = false
                secondButton.isEnabled = false
            } else {
                // No match, flip back the cards
                firstButton.text = ""
                secondButton.text = ""
                firstButton.isEnabled = true
                secondButton.isEnabled = true
            }

            // Reset indices
            firstCardIndex = null
            secondCardIndex = null

            // Check for game over
            if (matchedPairs == cards.size / 2) {
                endGame()
            } else {
                setAllButtonsEnabled(true)
            }
        }

        isCheckingMatch = false
    }

    private fun updateScores() {
        playerScoreView.text = "Score: $playerScore"
    }

    private fun setAllButtonsEnabled(enabled: Boolean) {
        cardButtons.forEach {
            if (it.text.isEmpty()) { // Only enable unmatched cards
                it.isEnabled = enabled
            }
        }
    }

    private fun endGame() {
        Toast.makeText(this, "You win! All pairs matched!", Toast.LENGTH_LONG).show()
        setAllButtonsEnabled(false)
    }

    private fun restartGame() {
        initializeGame()
        Toast.makeText(this, "Game restarted!", Toast.LENGTH_SHORT).show()
    }

    private fun resetGameState() {
        firstCardIndex = null
        secondCardIndex = null
        playerScore = 0
        matchedPairs = 0
        isCheckingMatch = false
        updateScores()
    }
}
