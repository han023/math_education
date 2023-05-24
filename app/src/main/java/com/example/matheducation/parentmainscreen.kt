package com.example.matheducation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.matheducation.databinding.ActivityParentmainscreenBinding

class parentmainscreen : AppCompatActivity() {

    private lateinit var binding : ActivityParentmainscreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityParentmainscreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var name = intent.getStringExtra("user").toString()
        var pass = intent.getStringExtra("pass").toString()
        var id = intent.getStringExtra("id").toString()

        binding.name.setText(name)

        binding.add.setOnClickListener {
            startActivity(Intent(this,addstudent::class.java))
        }

        binding.dis.setOnClickListener {
            val intent = Intent(this, viewscore::class.java)
            intent.putExtra("id", "-2")
            startActivity(intent)
        }

        binding.score.setOnClickListener {
            val intent = Intent(this, viewscore::class.java)
            intent.putExtra("id", "-4")
            startActivity(intent)
        }

        binding.allscore.setOnClickListener {
            val intent = Intent(this, viewscore::class.java)
            intent.putExtra("id", "-3")
            startActivity(intent)
        }

    }
}