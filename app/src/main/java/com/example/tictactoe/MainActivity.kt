package com.example.tictactoe

import android.os.Bundle
//import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
//import androidx.core.view.ViewCompat
//import androidx.core.view.WindowInsetsCompat
import android.view.View
import android.widget.Button
import android.widget.TextView
//import androidx.constraintlayout.widget.ConstraintLayout

class MainActivity : AppCompatActivity()
{
    private lateinit var buttons: Array<Array<Button>>
    private lateinit var turnTextView: TextView
    private lateinit var playAgainButton: Button
    private var currentPlayer = "X"
    private var movesCounter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        turnTextView = findViewById(R.id.text_turn)
        playAgainButton = findViewById(R.id.button_play_again)
        playAgainButton.visibility = View.GONE

        buttons = arrayOf(
            arrayOf(findViewById(R.id.b11), findViewById(R.id.b12), findViewById(R.id.b13)),
            arrayOf(findViewById(R.id.b21), findViewById(R.id.b22), findViewById(R.id.b23)),
            arrayOf(findViewById(R.id.b31), findViewById(R.id.b32), findViewById(R.id.b33))
        )

        for(i in buttons.indices)
        {
            for( j in buttons[i].indices)
            {
                buttons[i][j].setOnClickListener { onButtonClick(buttons[i][j], i, j) }
            }
        }

        playAgainButton.setOnClickListener { resetGame() }
    }

    private fun onButtonClick(button: Button, row: Int, col: Int)
    {
        if(button.text.isNotEmpty())
            return

        button.text = currentPlayer
        movesCounter++


        if(isWinner(row, col))
        {
            turnTextView.text = "Winner: $currentPlayer"
            endGame()
        }

        else if(movesCounter == 9)
        {
            turnTextView.text = "Draw"
            endGame()
        }

        else
        {
            currentPlayer = if(currentPlayer == "X") "O" else "X"
            turnTextView.text = "Turn $currentPlayer"
        }
    }

    private fun resetGame()
    {
        currentPlayer = "X"
        movesCounter = 0
        turnTextView.text = "Turn X"
        playAgainButton.visibility = View.GONE

        for(row in buttons)
        {
            for(b in row)
            {
                b.text = ""
                b.isEnabled = true
            }
        }
    }

    private fun isWinner(row:Int, col:Int): Boolean
    {
        if (buttons[row][0].text == currentPlayer &&
            buttons[row][1].text == currentPlayer &&
            buttons[row][2].text == currentPlayer
            ) return true

        if (buttons[0][col].text == currentPlayer &&
            buttons[1][col].text == currentPlayer &&
            buttons[2][col].text == currentPlayer
            ) return true

        if( row==col && buttons[0][0].text == currentPlayer &&
            buttons[1][1].text == currentPlayer &&
            buttons[2][2].text == currentPlayer
            ) return true

        if(row+col == 2 && buttons[0][2].text == currentPlayer &&
            buttons[1][1].text == currentPlayer &&
            buttons[2][0].text == currentPlayer
            ) return true

        return false
    }

    private fun endGame()
    {
        for(row in buttons)
        {
            for(button in row)
            {
                button.isEnabled = false
            }
        }
        playAgainButton.visibility = View.VISIBLE
    }
}