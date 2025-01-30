package com.example.retroboard


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import com.example.retroboard.R
import com.example.retroboard.TicTacToeActivity
import com.example.retroboard.MemoryGameActivity

import android.view.animation.AnimationUtils


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val ticTacToeButton = findViewById<Button>(R.id.btn_tic_tac_toe)
        ticTacToeButton.setOnClickListener {
            val animation = AnimationUtils.loadAnimation(this, R.anim.button_click)
            it.startAnimation(animation)
            val intent = Intent(this, TicTacToeActivity::class.java)
            startActivity(intent)
        }

        // Add Memory Game Button logic
        val memoryGameButton = findViewById<Button>(R.id.btn_memory_game)
        memoryGameButton.setOnClickListener {
            val animation = AnimationUtils.loadAnimation(this, R.anim.button_click)
            it.startAnimation(animation)
            val intent = Intent(this, MemoryGameActivity::class.java)
            startActivity(intent)
        }

        // Add Tapping Battle Game Button
        val tappingGameButton = findViewById<Button>(R.id.btn_tapping_game)
        tappingGameButton.setOnClickListener {
            val animation = AnimationUtils.loadAnimation(this, R.anim.button_click)
            it.startAnimation(animation)
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
        }

    }
}
