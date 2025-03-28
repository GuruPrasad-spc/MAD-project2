package com.example.mad_project

import LoginActivity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.EditText
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AlertDialog

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.secondpage)

        val backButton = findViewById<Button>(R.id.button5)
        val continueButton = findViewById<Button>(R.id.button6)
        val totalBudget = findViewById<EditText>(R.id.editTextNumber)
        val textView5 = findViewById<TextView>(R.id.textView5)

        val spinner1 = findViewById<Spinner>(R.id.spinner)
        val spinner2 = findViewById<Spinner>(R.id.spinner2)
        val spinner3 = findViewById<Spinner>(R.id.spinner3)
        val spinner4 = findViewById<Spinner>(R.id.spinner4)

        val spinnerOptions = arrayOf("Option 1", "Option 2", "Option 3", "Option 4")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, spinnerOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner1.adapter = adapter
        spinner2.adapter = adapter
        spinner3.adapter = adapter
        spinner4.adapter = adapter

        backButton.setOnClickListener {
            finish()
        }

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
                    0 -> startActivity(Intent(this, LoginActivity::class.java))
                    1 -> startActivity(Intent(this, register::class.java))
                }
            }
            .show()
    }
}
