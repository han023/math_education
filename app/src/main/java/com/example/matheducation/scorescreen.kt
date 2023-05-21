package com.example.matheducation

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.matheducation.databinding.ActivityScorescreenBinding
import java.util.*

class scorescreen : AppCompatActivity() {

    private lateinit var binding: ActivityScorescreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScorescreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getStringExtra("id").toString()
        val sco = intent.getStringExtra("sc").toString()
        binding.score.setText( "your score is "+sco )

        val dbb = splashscreen.dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("Date", currentdate() )
            put("Score", sco)
            put("StudentId", id)
        }
        val newRowId = dbb.insert("TTest", null, values)
        if (newRowId == -1L) {
            Toast.makeText(this, "Failed to insert values", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Values inserted successfully", Toast.LENGTH_SHORT).show()
        }


        binding.back.setOnClickListener {

            val intent = Intent(this, studentmainscreen::class.java)
            intent.putExtra("id", id)
            startActivity(intent)
            finish()
        }

    }

    fun currentdate():String{
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        return "$year-$month-$day"
    }

}