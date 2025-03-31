package com.example.mad_project

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class Main : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Load animations
        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        val slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up)

        // Initialize buttons
        val marriageButton = findViewById<MaterialButton>(R.id.marriage_button)
        val birthdayButton = findViewById<MaterialButton>(R.id.birthday_button)
        val eventButton = findViewById<MaterialButton>(R.id.event_button)
        val otherButton = findViewById<MaterialButton>(R.id.other_button)
        val loginButton = findViewById<MaterialButton>(R.id.login_button)
        val registerButton = findViewById<MaterialButton>(R.id.register_button)

        // Apply animations to buttons
        marriageButton.startAnimation(slideUp)
        birthdayButton.startAnimation(slideUp)
        eventButton.startAnimation(slideUp)
        otherButton.startAnimation(slideUp)
        loginButton.startAnimation(slideUp)
        registerButton.startAnimation(slideUp)

        // Add ripple effect to buttons
        marriageButton.setOnClickListener {
            it.startAnimation(fadeIn)
            navigateToSecondActivity("Marriage")
        }

        birthdayButton.setOnClickListener {
            it.startAnimation(fadeIn)
            navigateToSecondActivity("Birthday")
        }

        eventButton.setOnClickListener {
            it.startAnimation(fadeIn)
            navigateToSecondActivity("Event")
        }

        otherButton.setOnClickListener {
            it.startAnimation(fadeIn)
            navigateToSecondActivity("Other")
        }

        // Navigate to LoginActivity with animation
        loginButton.setOnClickListener {
            it.startAnimation(fadeIn)
            startActivity(Intent(this, login::class.java))
            overridePendingTransition(R.anim.fade_in, R.anim.slide_up)
        }

        // Navigate to RegisterActivity with animation
        registerButton.setOnClickListener {
            it.startAnimation(fadeIn)
            startActivity(Intent(this, RegisterActivity::class.java))
            overridePendingTransition(R.anim.fade_in, R.anim.slide_up)
        }
    }

    private fun navigateToSecondActivity(eventType: String) {
        val intent = Intent(this, second::class.java).apply {
            putExtra("EVENT_TYPE", eventType)
        }
        startActivity(intent)
        overridePendingTransition(R.anim.fade_in, R.anim.slide_up)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_up, R.anim.fade_in)
    }
}
