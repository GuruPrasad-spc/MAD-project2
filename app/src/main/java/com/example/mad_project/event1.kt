package com.example.mad_project

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.mad_project.R

class event1 : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.event1)
        val eventlist= listOf("person1","person2","person3");
        val listview=findViewById<ListView>(R.id.List);

        val adapter=ArrayAdapter(this,android.R.layout.simple_list_item_1,eventlist);
        listview.adapter=adapter;

    }
}

