package com.example.mad_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.mad_project.R
import com.example.mad_project.SecondActivity

import com.example.mad_project.RegisterActivity

class MainActivity : AppCompatActivity() {

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

        // Navigate to SecondPage activity for event buttons
        val eventIntent = Intent(this, SecondActivity::class.java)

        marriageButton.setOnClickListener {
            startActivity(eventIntent)
        }

        birthdayButton.setOnClickListener {
            startActivity(eventIntent)
        }

        eventButton.setOnClickListener {
            startActivity(eventIntent)
        }

        otherButton.setOnClickListener {
            startActivity(eventIntent)
        }

        // Navigate to LoginActivity for login button
        loginButton.setOnClickListener {
            val loginIntent = Intent(this, LoginActivity::class.java)
            startActivity(loginIntent)
        }

        registerButton.setOnClickListener {
            val registerIntent = Intent(this, RegisterActivity::class.java)
            startActivity(registerIntent)
        }

    }
    }

