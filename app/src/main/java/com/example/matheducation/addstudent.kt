package com.example.matheducation

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.matheducation.databinding.ActivityAddstudentBinding

class addstudent : AppCompatActivity() {

    private lateinit var binding:ActivityAddstudentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddstudentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.add.setOnClickListener {

            val user = binding.username.text.toString().trim()
            val pass = binding.password.text.toString().trim()

            if (user == "" || pass ==""){
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show()
            } else {

                var UserName = ""
                splashscreen.dbHelper.openDatabase()
                val cursor = splashscreen.dbHelper.database?.rawQuery("SELECT * from TStudent where UserName == '$user' AND UserPassWord = '$pass'", null)
                if (cursor != null && cursor.moveToFirst()) {
                    UserName = cursor.getString(cursor.getColumnIndexOrThrow("UserName"))
                }

                if (UserName == ""){


                    val dbb = splashscreen.dbHelper.writableDatabase
                    val values = ContentValues().apply {
                        put("UserName", user)
                        put("UserPassWord", pass)
                    }
                    val newRowId = dbb.insert("TStudent", null, values)
                    if (newRowId == -1L) {
                        Toast.makeText(this, "Failed to insert values", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Values inserted successfully", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    cursor!!.close()
                    splashscreen.dbHelper.close()
                }else {
                    Toast.makeText(this, "already have account", Toast.LENGTH_SHORT).show()
                }
            }

        }

    }
}