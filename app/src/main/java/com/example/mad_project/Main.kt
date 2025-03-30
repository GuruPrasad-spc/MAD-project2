package com.example.mad_project

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class Main : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize buttons
        val marriageButton = findViewById<Button>(R.id.marriage_button)
        val birthdayButton = findViewById<Button>(R.id.birthday_button)
        val eventButton = findViewById<Button>(R.id.event_button)
        val otherButton = findViewById<Button>(R.id.other_button)
        val loginButton = findViewById<Button>(R.id.login_button)
        val registerButton = findViewById<Button>(R.id.register_button)

        // Navigate to SecondActivity with event type
        marriageButton.setOnClickListener {
            navigateToSecondActivity("Marriage")
        }

        birthdayButton.setOnClickListener {
            navigateToSecondActivity("Birthday")
        }

        eventButton.setOnClickListener {
            navigateToSecondActivity("Event")
        }

        otherButton.setOnClickListener {
            navigateToSecondActivity("Other")
        }

        // Navigate to LoginActivity
        loginButton.setOnClickListener {
            startActivity(Intent(this, login::class.java))
        }

        // Navigate to RegisterActivity
        registerButton.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun navigateToSecondActivity(eventType: String) {
        val intent = Intent(this, second::class.java).apply {
            putExtra("EVENT_TYPE", eventType) // Pass event type
        }
        startActivity(intent)
    }
}
