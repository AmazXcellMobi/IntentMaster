package com.ggcs.intentmaster

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class ResultActivity : AppCompatActivity() {

    private lateinit var sendBackButton: Button
    private lateinit var motivationTextView: TextView
    private lateinit var userName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        setupStatusBar()
        initializeViews()
        retrieveUserName()
        displayMotivationalMessage()
        setupSendBackButton()
    }

    // Sets the status bar color
    private fun setupStatusBar() {
        window.statusBarColor = ContextCompat.getColor(this, R.color.teal_700)
        window.decorView.systemUiVisibility = 0
    }

    // Binds views
    private fun initializeViews() {
        motivationTextView = findViewById(R.id.textViewMotivation)
        sendBackButton = findViewById(R.id.btnSendBack)
    }

    // Retrieves the passed user name
    private fun retrieveUserName() {
        userName = intent.getStringExtra("user_name") ?: "User"
    }

    // Shows motivational message
    private fun displayMotivationalMessage() {
        val message = "You're doing great, $userName! Keep going! 💪"
        motivationTextView.text = message
    }

    // Sets up the return result button
    private fun setupSendBackButton() {
        sendBackButton.setOnClickListener {
            val messageToReturn = motivationTextView.text.toString()
            val resultIntent = Intent().apply {
                putExtra("result_message", messageToReturn)
            }
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }
}
