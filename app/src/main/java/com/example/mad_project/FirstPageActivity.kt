package com.example.mad_project

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class FirstPageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.firstpage)

        // Find the continue button
        val continueButton = findViewById<Button>(R.id.button)

        // Set click listener for the continue button
        continueButton.setOnClickListener {
            // Navigate to Main activity
            startActivity(Intent(this, Main::class.java))
            finish() // Close this activity
        }
    }
} 