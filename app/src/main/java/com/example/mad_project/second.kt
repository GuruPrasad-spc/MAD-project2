package com.example.mad_project

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
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
        val summaryTextView = findViewById<TextView>(R.id.summaryText)
        val actualBudgetText = findViewById<TextView>(R.id.actualBudgetText)

        val spinner1 = findViewById<Spinner>(R.id.spinner)
        val spinner2 = findViewById<Spinner>(R.id.spinner2)
        val spinner3 = findViewById<Spinner>(R.id.spinner3)
        val spinner4 = findViewById<Spinner>(R.id.spinner4)

        // Get event type from MainActivity
        val eventType = intent.getStringExtra("EVENT_TYPE") ?: "Event"
        textView5.text = "Selected Event: $eventType"

        // Spinner options
        val spinnerOptions = arrayOf("Food", "Decoration", "Drinks", "Snacks")
        
        // Create custom adapter with black text color
        val adapter = object : ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerOptions) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent)
                (view as? TextView)?.setTextColor(0xFF000000.toInt()) // Black color
                return view
            }

            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getDropDownView(position, convertView, parent)
                (view as? TextView)?.setTextColor(0xFF000000.toInt()) // Black color
                return view
            }
        }
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner1.adapter = adapter
        spinner2.adapter = adapter
        spinner3.adapter = adapter
        spinner4.adapter = adapter

        // Add TextWatcher for real-time budget calculation
        totalBudget.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val budgetText = s.toString()
                if (budgetText.isNotEmpty()) {
                    try {
                        val budget = budgetText.toDouble()
                        val venue = budget * 0.4
                        val catering = budget * 0.25
                        val decoration = budget * 0.15
                        val entertainment = budget * 0.1
                        val miscellaneous = budget * 0.1

                        val result = "Venue: \$${"%.2f".format(venue)}\n" +
                                "Catering: \$${"%.2f".format(catering)}\n" +
                                "Decoration: \$${"%.2f".format(decoration)}\n" +
                                "Entertainment: \$${"%.2f".format(entertainment)}\n" +
                                "Miscellaneous: \$${"%.2f".format(miscellaneous)}"

                        actualBudgetText.text = result
                    } catch (e: NumberFormatException) {
                        actualBudgetText.text = "Please enter a valid number"
                    }
                } else {
                    actualBudgetText.text = "Enter your budget to see the distribution"
                }
            }
        })

        // Back button
        backButton.setOnClickListener {
            finish()
        }

        // Continue button - Calculate budget split and check login state
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
                    // User is already logged in, navigate to EventPlannersActivity with budget
                    val intent = Intent(this, EventPlannersActivity::class.java)
                    intent.putExtra("budget", budget)
                    startActivity(intent)
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
            .setTitle("Please Login or Register")
            .setMessage("You need to be logged in to continue booking")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> {
                        val intent = Intent(this, login::class.java)
                        intent.putExtra("NAVIGATE_TO_EVENT", true)
                        startActivity(intent)
                    }
                    1 -> {
                        val intent = Intent(this, RegisterActivity::class.java)
                        intent.putExtra("NAVIGATE_TO_EVENT", true)
                        startActivity(intent)
                    }
                }
            }
            .setCancelable(false) // User must choose an option
            .show()
    }
}
