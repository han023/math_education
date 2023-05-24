package com.example.matheducation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.matheducation.adapter.viewscoreadapter
import com.example.matheducation.databinding.ActivityFinalscoreBinding
import com.example.matheducation.model.viewscoremodel

class finalscore : AppCompatActivity() {

    private lateinit var binding:ActivityFinalscoreBinding
    private lateinit var l:ArrayList<viewscoremodel>;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinalscoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var name = intent.getStringExtra("name").toString()
        var id = intent.getStringExtra("id").toString()

        l = ArrayList<viewscoremodel>()
        binding.recycler.layoutManager = LinearLayoutManager(this)

        splashscreen.dbHelper.openDatabase()
        val cursor = splashscreen.dbHelper.database?.rawQuery(
            "SELECT * from TTest where StudentId == '$id'",
            null
        )!!
        if (cursor != null && cursor.moveToFirst()) {
            do {
                val score = cursor.getString(cursor.getColumnIndexOrThrow("Score"))
                val date = cursor.getString(cursor.getColumnIndexOrThrow("Date"))
            val v = viewscoremodel(score, date,"")
            l.add(v)
        } while (cursor.moveToNext())
        }
        cursor?.close()
        splashscreen.dbHelper.close()

        if (!l.isEmpty()) {
            binding.recycler.adapter = viewscoreadapter(l,this,id)
        }else{
            Toast.makeText(this,"No Record found", Toast.LENGTH_SHORT).show()
        }

        if (!l.isEmpty()) {
            binding.recycler.adapter = viewscoreadapter(l,this,id)
        }else{
            Toast.makeText(this,"No Record found", Toast.LENGTH_SHORT).show()
        }

    }
}