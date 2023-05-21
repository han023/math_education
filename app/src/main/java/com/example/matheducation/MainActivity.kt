package com.example.matheducation

import DatabaseHelper
import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.matheducation.databinding.ActivityMainBinding
import com.example.matheducation.splashscreen.Companion.dbHelper

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.student.setOnClickListener {
            val user = binding.username.text.toString().trim()
            val pass = binding.password.text.toString().trim()

            if (user == "" || pass ==""){
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show()
            } else {

                var UserName = ""
                var UserPassWord = ""
                var id = ""

                try {
                    dbHelper.openDatabase()
                    val cursor = dbHelper.database?.rawQuery(
                        "SELECT * from TStudent where UserName == '$user' AND UserPassWord = '$pass'",
                        null
                    )
                    if (cursor != null && cursor.moveToFirst()) {
                        UserName = cursor.getString(cursor.getColumnIndexOrThrow("UserName"))
                        UserPassWord =
                            cursor.getString(cursor.getColumnIndexOrThrow("UserPassWord"))
                        id = cursor.getString(cursor.getColumnIndexOrThrow("Id"))
                    }
                }catch (e:Exception){
                    dbHelper.openDatabase()
                }

                if (UserName == ""){
                    Toast.makeText(this, "no account", Toast.LENGTH_SHORT).show()
                }else {
                    val intent = Intent(this, studentmainscreen::class.java)
                    intent.putExtra("user", UserName)
                    intent.putExtra("pass", UserPassWord)
                    intent.putExtra("id", id)
                    startActivity(intent)
                    Toast.makeText(this, "have", Toast.LENGTH_SHORT).show()
                }

            }

        }

        binding.parent.setOnClickListener {


            val user = binding.username.text.toString().trim()
            val pass = binding.password.text.toString().trim()

            if (user == "" || pass ==""){
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show()
            } else {

                var UserName = ""
                var UserPassWord = ""
                var id = ""

                val cursor = dbHelper.database?.rawQuery("SELECT * from TParent where UserName == '$user' AND UserPassWord = '$pass'", null)
                if (cursor != null && cursor.moveToFirst()) {
                    UserName = cursor.getString(cursor.getColumnIndexOrThrow("UserName"))
                }

                if (UserName == ""){


                    val dbb = dbHelper.writableDatabase
                    val values = ContentValues().apply {
                        put("UserName", user)
                        put("UserPassWord", pass)
                    }
                    val newRowId = dbb.insert("TParent", null, values)
                    if (newRowId == -1L) {
                        Toast.makeText(this, "Failed to insert values", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Values inserted successfully", Toast.LENGTH_SHORT).show()


                        val cursor = dbHelper.database?.rawQuery("SELECT * from TParent where Id == '$newRowId'", null)
                        if (cursor != null && cursor.moveToFirst()) {
                            UserName = cursor.getString(cursor.getColumnIndexOrThrow("UserName"))
                            UserPassWord = cursor.getString(cursor.getColumnIndexOrThrow("UserPassWord"))
                            id = cursor.getString(cursor.getColumnIndexOrThrow("Id"))
                        }

                        val intent = Intent(this, parentmainscreen::class.java)
                        intent.putExtra("user", UserName)
                        intent.putExtra("pass", UserPassWord)
                        intent.putExtra("id", id)
                        startActivity(intent)
                    }
                }else {
                    Toast.makeText(this, "already have account", Toast.LENGTH_SHORT).show()

                    val cursor = dbHelper.database?.rawQuery("SELECT * from TParent where UserName == '$user' AND UserPassWord = '$pass'", null)
                    if (cursor != null && cursor.moveToFirst()) {
                        UserName = cursor.getString(cursor.getColumnIndexOrThrow("UserName"))
                        UserPassWord = cursor.getString(cursor.getColumnIndexOrThrow("UserPassWord"))
                        id = cursor.getString(cursor.getColumnIndexOrThrow("Id"))
                    }




                    val intent = Intent(this, parentmainscreen::class.java)
                    intent.putExtra("user", UserName)
                    intent.putExtra("pass", UserPassWord)
                    intent.putExtra("id", id)
                    startActivity(intent)
                }
            }



        }

    }

    override fun onDestroy() {
        super.onDestroy()
        dbHelper.close()
    }


}