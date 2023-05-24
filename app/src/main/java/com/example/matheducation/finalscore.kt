package com.example.matheducation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.matheducation.databinding.ActivityFinalscoreBinding
import com.example.matheducation.model.viewscoremodel

class finalscore : AppCompatActivity() {

    private lateinit var binding:ActivityFinalscoreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinalscoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var name = intent.getStringExtra("name").toString()
        var id = intent.getStringExtra("id").toString()

        binding.name.setText("name : "+name)

        val cursor = splashscreen.dbHelper.database?.rawQuery(
            "SELECT * from TTest where StudentId == '$id'",
            null
        )!!
        if (cursor != null && cursor.moveToFirst()) {
                val score = cursor.getString(cursor.getColumnIndexOrThrow("Score"))
                val date = cursor.getString(cursor.getColumnIndexOrThrow("Date"))
            binding.score.setText("Score : "+score)
            binding.date.setText("Date : "+date)
        }
        cursor?.close()

    }
}