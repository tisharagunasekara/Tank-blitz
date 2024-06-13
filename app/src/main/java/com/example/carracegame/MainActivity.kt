package com.example.carracegame
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : AppCompatActivity(), GameTask {
   private lateinit var rootLayout: LinearLayout
   private lateinit var startBtn: Button
   private lateinit var mGameView: GameView
   private lateinit var scoreTextView: TextView
   private var currentScore = 0
    private lateinit var highScoreTextView: TextView

     private lateinit var sharedPreferences: SharedPreferences
    private lateinit var imageView: ImageView
    private lateinit var greatImageView: ImageView
    private lateinit var loseImageView: ImageView
    private lateinit var tittle:ImageView
    private lateinit var help:Button

    private var highScore = 0

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        highScoreTextView = findViewById(R.id.highScoreTextView)
        rootLayout = findViewById(R.id.instruction)
        startBtn = findViewById(R.id.startbtn)
        scoreTextView = findViewById(R.id.score)
        imageView = findViewById(R.id.imageView)
        tittle =findViewById(R.id.logo)
        help =findViewById(R.id.instbtn)

        sharedPreferences = getSharedPreferences("HighScores", Context.MODE_PRIVATE)
        greatImageView = findViewById(R.id.win)
        loseImageView = findViewById(R.id.lose)

        // Retrieve high score from SharedPreferences
        highScore = sharedPreferences.getInt("highScore", 0)
        // Retrieve and display high score
        val highScore = sharedPreferences.getInt("highScore", 0)
        highScoreTextView.text = "High Score: $highScore"

        mGameView = GameView(this, gameTask = this)

        startBtn.setOnClickListener {
            mGameView.setBackgroundResource(R.drawable.bg)
            rootLayout.addView(mGameView)
            startBtn.visibility = View.GONE
            scoreTextView.visibility = View.GONE
            highScoreTextView.visibility = View.VISIBLE
            imageView.visibility=View.GONE
            greatImageView.visibility = View.GONE
            loseImageView.visibility = View.GONE
            tittle.visibility = View.GONE
            help.visibility = View.GONE
            currentScore = 0


        }
        //navigate to the HElp page
        help.setOnClickListener {
            val intent = Intent(this, Frontpage::class.java)
            startActivity(intent)
        }

    }


    @SuppressLint("SetTextI18n")
    override fun closeGame(mScore: Int) {
        // Update score and display
        currentScore = mScore
        scoreTextView.text = "Score: $currentScore"

        // Save high score
        val highScore = sharedPreferences.getInt("highScore", 0)
        if (currentScore > highScore) {
            val editor = sharedPreferences.edit()
            editor.putInt("highScore", currentScore)
            editor.apply()
        }
        highScoreTextView.visibility = View.VISIBLE
        highScoreTextView.text = "High Score: ${sharedPreferences.getInt("highScore", 0)}"

        if (currentScore > highScore) {
            greatImageView.visibility = View.VISIBLE
            loseImageView.visibility = View.GONE
        } else {
            greatImageView.visibility = View.GONE
            loseImageView.visibility = View.VISIBLE
        }

        rootLayout.removeView(mGameView)
        startBtn.visibility = View.VISIBLE
        scoreTextView.visibility = View.VISIBLE
        imageView.visibility=View.GONE
        tittle.visibility=View.GONE
        help.visibility = View.GONE

        startBtn.setOnClickListener{
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }
}
