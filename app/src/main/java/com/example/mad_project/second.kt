package com.example.mad_project

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class second : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.secondpage)

        // UI Elements
        val backButton = findViewById<Button>(R.id.button5)
        val continueButton = findViewById<Button>(R.id.button6)
        val totalBudget = findViewById<EditText>(R.id.editTextNumber)
        val textView5 = findViewById<TextView>(R.id.textView5)

        val spinner1 = findViewById<Spinner>(R.id.spinner)
        val spinner2 = findViewById<Spinner>(R.id.spinner2)
        val spinner3 = findViewById<Spinner>(R.id.spinner3)
        val spinner4 = findViewById<Spinner>(R.id.spinner4)

        // Get event type from MainActivity
        val eventType = intent.getStringExtra("EVENT_TYPE") ?: "Event"
        textView5.text = "Selected Event: $eventType"

        // Spinner options
        val spinnerOptions = arrayOf("Food", "Decoration", "Drinks", "Snacks")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, spinnerOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner1.adapter = adapter
        spinner2.adapter = adapter
        spinner3.adapter = adapter
        spinner4.adapter = adapter

        // Back button
        backButton.setOnClickListener {
            finish()
        }

        // Continue button - Show Login/Register Dialog
        continueButton.setOnClickListener {
            showLoginRegisterDialog()
        }
    }

    private fun showLoginRegisterDialog() {
        val options = arrayOf("Login", "Register")
        AlertDialog.Builder(this)
            .setTitle("Choose an option")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> startActivity(Intent(this, login::class.java))
                    1 -> startActivity(Intent(this, RegisterActivity::class.java))
                }
            }
            .show()
    }
}
