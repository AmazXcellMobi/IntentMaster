package com.ggcs.intentmaster

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class SecondActivity : AppCompatActivity() {

    private lateinit var greetingTextView: TextView
    private lateinit var resultTextView: TextView
    private lateinit var shareButton: Button
    private lateinit var getMessageButton: Button
    private lateinit var openWebPageButton: Button
    private lateinit var userName: String

    // ActivityResultLauncher to handle result from ResultActivity
    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val returnedMessage = result.data?.getStringExtra("result_message")
            resultTextView.text = returnedMessage
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        setupStatusBar()
        initializeViews()
        retrieveUserName()
        displayGreeting()
        setupButtonActions()
    }

    // Sets the status bar color
    private fun setupStatusBar() {
        window.statusBarColor = ContextCompat.getColor(this, R.color.teal_700)
        window.decorView.systemUiVisibility = 0
    }

    // Binds views
    private fun initializeViews() {
        greetingTextView = findViewById(R.id.textViewGreeting)
        resultTextView = findViewById(R.id.textViewResult)
        shareButton = findViewById(R.id.btnShare)
        getMessageButton = findViewById(R.id.btnGetMessage)
        openWebPageButton = findViewById(R.id.btnOpenWebPage)
    }

    // Retrieves the passed user name
    private fun retrieveUserName() {
        userName = intent.getStringExtra("user_name") ?: "User"
    }

    // Updates greeting text
    private fun displayGreeting() {
        greetingTextView.text = "Hello, $userName"
    }

    // Configures button listeners
    private fun setupButtonActions() {
        shareButton.setOnClickListener { shareGreetingText() }
        getMessageButton.setOnClickListener { openResultActivity() }
        openWebPageButton.setOnClickListener { openWebPage() }
    }
    // Opens the system share sheet
    private fun shareGreetingText() {
        val shareText = "Hello, I'm $userName"
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, shareText)
        }
        startActivity(Intent.createChooser(shareIntent, "Share via"))
    }

    // Opens a web browser to a predefined URL
    private fun openWebPage() {
        val url = "https://www.example.com"
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        val chooserIntent = Intent.createChooser(browserIntent, "Open with")

        try {
            startActivity(chooserIntent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, "No browser found to open the web page.", Toast.LENGTH_SHORT).show()
        }
    }

    // Launches ResultActivity for result
    private fun openResultActivity() {
        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra("user_name", userName)
        resultLauncher.launch(intent)
    }
}
