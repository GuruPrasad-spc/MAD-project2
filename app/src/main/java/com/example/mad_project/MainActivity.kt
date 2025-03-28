package com.example.mad_project


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.marriage_button)
        button.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }
        val button1 =findViewById<Button>(R.id.login_button)
        button1.setOnClickListener{
            val intent= Intent(this,LoginActivity:: class.java)
            startActivity(intent)
        }
        val button2 =findViewById<Button>(R.id.register_button)
        button2.setOnClickListener{
            val intent= Intent(this,register:: class.java)
            startActivity(intent)
        }
    }
}
