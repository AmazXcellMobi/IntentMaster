package com.ggcs.intentmaster

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private lateinit var nameInputField: EditText
    private lateinit var enterButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupStatusBar()
        initializeViews()
        handleEditorAction()
        handleEnterButtonClick()
    }

    // Sets the status bar color
    private fun setupStatusBar() {
        window.statusBarColor = ContextCompat.getColor(this, R.color.teal_700)
        window.decorView.systemUiVisibility = 0 // default (white icons)
    }

    // Binds views
    private fun initializeViews() {
        nameInputField = findViewById(R.id.editTextName)
        enterButton = findViewById(R.id.btnEnter)
    }

    // Dismisses keyboard on 'Done' press
    private fun handleEditorAction() {
        nameInputField.setOnEditorActionListener { view, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
                view.clearFocus()
                true
            } else {
                false
            }
        }
    }

    // Starts SecondActivity with the entered name
    private fun handleEnterButtonClick() {
        enterButton.setOnClickListener {
            val name = nameInputField.text.toString().trim()
            if (name.isNotEmpty()) {
                val intent = Intent(this, SecondActivity::class.java)
                intent.putExtra("user_name", name)
                startActivity(intent)
            } else {
                nameInputField.error = "Please enter your name"
            }
        }
    }
}
