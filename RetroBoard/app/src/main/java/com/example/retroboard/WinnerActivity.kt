package com.example.retroboard
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.retroboard.R

class WinnerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_winner)

        val winner = intent.getStringExtra("winner") ?: "Unknown"
        val winnerText: TextView = findViewById(R.id.winnerText)
        val playAgainButton: Button = findViewById(R.id.playAgainButton)

        winnerText.text = "$winner Wins!"

        playAgainButton.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
