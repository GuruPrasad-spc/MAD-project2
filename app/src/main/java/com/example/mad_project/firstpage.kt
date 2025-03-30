package com.example.mad_project

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.mad_project.R

class firstpage : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.firstpage)
        val continueButton = findViewById<Button>(R.id.button)
        continueButton .setOnClickListener(){
            val intent = Intent(this, Main::class.java)
            startActivity(intent)

        }

    }
}

