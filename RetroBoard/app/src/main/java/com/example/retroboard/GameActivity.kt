package com.example.retroboard

import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout

class GameActivity : AppCompatActivity() {

    private lateinit var redBar: View
    private lateinit var blueBar: View
    private lateinit var redTapArea: View
    private lateinit var blueTapArea: View
    private lateinit var gameLayout: FrameLayout

    private var redHeight = 0.0f
    private var blueHeight = 0.0f
    private var screenHeight = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val displayMetrics = resources.displayMetrics
        screenHeight = displayMetrics.heightPixels

        redBar = findViewById(R.id.redBar)
        blueBar = findViewById(R.id.blueBar)
        redTapArea = findViewById(R.id.redTapArea)
        blueTapArea = findViewById(R.id.blueTapArea)
        gameLayout = findViewById(R.id.gameLayout)

        gameLayout.setOnTouchListener { _, event -> handleTouch(event) }
    }

    private fun handleTouch(event: MotionEvent): Boolean {
        val pointerCount = event.pointerCount

        for (i in 0 until pointerCount) {
            val x = event.getX(i)
            val y = event.getY(i)

            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN, MotionEvent.ACTION_POINTER_DOWN -> {
                    if (isViewUnder(redTapArea, x, y)) {
                        redHeight += 0.05f
                        updateBar(redBar, redHeight)
                        checkWinner("Red")
                    } else if (isViewUnder(blueTapArea, x, y)) {
                        blueHeight += 0.05f
                        updateBar(blueBar, blueHeight)
                        checkWinner("Blue")
                    }
                }
            }
        }
        return true
    }

    private fun isViewUnder(view: View, x: Float, y: Float): Boolean {
        val location = IntArray(2)
        view.getLocationOnScreen(location)
        val viewX = location[0]
        val viewY = location[1]
        return x >= viewX && x <= viewX + view.width && y >= viewY && y <= viewY + view.height
    }

    private fun updateBar(bar: View, heightPercent: Float) {
        val layoutParams = bar.layoutParams as ConstraintLayout.LayoutParams
        layoutParams.matchConstraintPercentHeight = heightPercent
        bar.layoutParams = layoutParams
    }

    private fun checkWinner(player: String) {
        if (redHeight >= 1.0f || blueHeight >= 1.0f) {
            val intent = Intent(this, WinnerActivity::class.java)
            intent.putExtra("winner", player)
            startActivity(intent)
            finish()
        }
    }
}
