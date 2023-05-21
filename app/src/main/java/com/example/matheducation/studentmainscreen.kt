package com.example.matheducation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.matheducation.databinding.ActivityStudentmainscreenBinding

class studentmainscreen : AppCompatActivity() {

    private lateinit var binding : ActivityStudentmainscreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentmainscreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra("user").toString()
        val id = intent.getStringExtra("id").toString()

        binding.test.setOnClickListener {
            val intent = Intent(this, test::class.java)
            intent.putExtra("id", id)
            intent.putExtra("name", name)
            startActivity(intent)
        }

        binding.score.setOnClickListener {
            val intent = Intent(this, viewscore::class.java)
            intent.putExtra("id", id)
            startActivity(intent)
        }

    }
}