package com.example.matheducation

import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.matheducation.adapter.viewscoreadapter
import com.example.matheducation.databinding.ActivityViewscoreBinding
import com.example.matheducation.model.viewscoremodel

class viewscore : AppCompatActivity() {

    private lateinit var binding:ActivityViewscoreBinding
    private lateinit var l:ArrayList<viewscoremodel>;
    private lateinit var cursor:Cursor;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewscoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getStringExtra("id").toString()

        l = ArrayList<viewscoremodel>()
        binding.recycler.layoutManager = LinearLayoutManager(this)

        splashscreen.dbHelper.openDatabase()
        if(id == "-2"||id=="-4") {
            cursor = splashscreen.dbHelper.database?.rawQuery("SELECT * FROM TStudent", null)!!
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    val score = cursor.getString(cursor.getColumnIndexOrThrow("UserName"))
                    val date = cursor.getString(cursor.getColumnIndexOrThrow("UserPassWord"))
                    val id = cursor.getString(cursor.getColumnIndexOrThrow("Id"))
                    val v = viewscoremodel(score, date,id)
                    l.add(v)
                } while (cursor.moveToNext())
            }
            cursor?.close()
            splashscreen.dbHelper.close()
        } else if (id == "-3"){
            cursor = splashscreen.dbHelper.database?.rawQuery("SELECT * from TTest as t join TStudent as s on StudentId ", null)!!
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    val score = cursor.getString(cursor.getColumnIndexOrThrow("UserName"))
                    val score2 = cursor.getString(cursor.getColumnIndexOrThrow("Score"))
                    val date = cursor.getString(cursor.getColumnIndexOrThrow("Date"))
                    val v = viewscoremodel(score+","+score2, date,"")
                    l.add(v)
                } while (cursor.moveToNext())
            }
            cursor?.close()
            splashscreen.dbHelper.close()
        }else {
            if (id == "-1") {
                cursor = splashscreen.dbHelper.database?.rawQuery("SELECT * from TTest", null)!!
            } else {
                cursor = splashscreen.dbHelper.database?.rawQuery(
                    "SELECT * from TTest where StudentId == '$id'",
                    null
                )!!
            }
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
        }

        if (!l.isEmpty()) {
            binding.recycler.adapter = viewscoreadapter(l,this,id)
        }else{
            Toast.makeText(this,"No Record found",Toast.LENGTH_SHORT).show()
        }

    }
}