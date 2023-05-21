package com.example.matheducation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.matheducation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.student.setOnClickListener {
            val user = binding.student.text.toString().trim()
            val pass = binding.password.text.toString().trim()

            if (user == "" || pass ==""){
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show()
            } else {

            }

        }

        binding.parent.setOnClickListener {

        }

    }
}