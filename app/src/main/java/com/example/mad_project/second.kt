package com.example.mad_project

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class second : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.secondpage)

        auth = FirebaseAuth.getInstance()

        // UI Elements
        val backButton = findViewById<Button>(R.id.button5)
        val continueButton = findViewById<Button>(R.id.button6)
        val totalBudget = findViewById<EditText>(R.id.editTextNumber)
        val textView5 = findViewById<TextView>(R.id.textView5)
        val summaryTextView = findViewById<TextView>(R.id.textView5) // TextView for displaying results

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

        // Continue button - Calculate budget split and show Login/Register Dialog
        continueButton.setOnClickListener {
            val budgetText = totalBudget.text.toString()
            if (budgetText.isNotEmpty()) {
                val budget = budgetText.toDouble()
                val pref1 = budget * 0.4
                val pref2 = budget * 0.3
                val pref3 = budget * 0.2
                val pref4 = budget * 0.1

                val result = "Budget Allocation:\n${spinner1.selectedItem}: \$${"%.2f".format(pref1)}\n" +
                        "${spinner2.selectedItem}: \$${"%.2f".format(pref2)}\n" +
                        "${spinner3.selectedItem}: \$${"%.2f".format(pref3)}\n" +
                        "${spinner4.selectedItem}: \$${"%.2f".format(pref4)}"

                summaryTextView.text = result // Display split amounts
                
                // Check if user is already logged in
                if (auth.currentUser != null) {
                    // User is already logged in, navigate to EventActivity
                    startActivity(Intent(this, EventActivity::class.java))
                    finish()
                } else {
                    // User is not logged in, show login/register dialog
                    showLoginRegisterDialog()
                }
            } else {
                Toast.makeText(this, "Please enter a total budget", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showLoginRegisterDialog() {
        val options = arrayOf("Login", "Register")
        AlertDialog.Builder(this)
            .setTitle("Choose an option")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> {
                        val intent = Intent(this, login::class.java)
                        intent.putExtra("NAVIGATE_TO_EVENT", true)
                        startActivity(intent)
                    }
                    1 -> startActivity(Intent(this, RegisterActivity::class.java))
                }
            }
            .show()
    }
}
