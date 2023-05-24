package com.example.matheducation

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.UserManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.matheducation.databinding.ActivityMainBinding
import com.example.matheducation.permission.geopermission
import com.example.matheducation.splashscreen.Companion.dbHelper
import com.example.matheducation.splashscreen.Companion.sharedPreferences


class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        geopermission.requestPermissions(this);

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
                    cursor!!.close()
                    dbHelper.close()
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

                dbHelper.openDatabase()
                if (sharedPreferences.contains("username")) {
                    sharedPreferences.edit().putString("username", user).apply()
                    val cursor = dbHelper.database?.rawQuery(
                        "SELECT * from TParent where UserName == '$user' and UserPassWord == '$pass'",
                        null
                    )
                    if (cursor != null && cursor.moveToFirst()) {
                        UserName = cursor.getString(cursor.getColumnIndexOrThrow("UserName"))
                        UserPassWord = cursor.getString(cursor.getColumnIndexOrThrow("UserPassWord"))
                        id = cursor.getString(cursor.getColumnIndexOrThrow("Id"))
                    }
                    if(UserName != "") {
                        val intent = Intent(this, parentmainscreen::class.java)
                        intent.putExtra("user", UserName)
                        intent.putExtra("pass", UserPassWord)
                        intent.putExtra("id", id)
                        startActivity(intent)
                        cursor!!.close()
                        dbHelper.close()
                    }else{
                        Toast.makeText(this,"parent not exist ",Toast.LENGTH_SHORT).show()
                    }

                } else {
                    sharedPreferences.edit().putString("username", user).apply()
                    val dbb = dbHelper.writableDatabase
                    val values = ContentValues().apply {
                        put("UserName", user)
                        put("UserPassWord", pass)
                    }
                    val newRowId = dbb.insert("TParent", null, values)
                    if (newRowId == -1L) {
                        Toast.makeText(this, "Failed to insert values", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Values inserted successfully", Toast.LENGTH_SHORT)
                            .show()

                        val cursor = dbHelper.database?.rawQuery("SELECT * from TParent where UserName == '$user' and UserPassWord == '$pass'", null)
                        if (cursor != null && cursor.moveToFirst()) {
                            UserName = cursor.getString(cursor.getColumnIndexOrThrow("UserName"))
                            UserPassWord = cursor.getString(cursor.getColumnIndexOrThrow("UserPassWord"))
                            id = cursor.getString(cursor.getColumnIndexOrThrow("Id"))
                        }
                        cursor!!.close()
                        dbHelper.close()
                        val intent = Intent(this, parentmainscreen::class.java)
                        intent.putExtra("user", UserName)
                        intent.putExtra("pass", UserPassWord)
                        intent.putExtra("id", id)
                        startActivity(intent)
                        cursor!!.close()
                        dbHelper.close()
                    }

                }

            }



        }

    }

    override fun onDestroy() {
        super.onDestroy()
        dbHelper.close()
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        results: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, results)
        if (!geopermission.hasGeoPermissions(this)) {
            // Use toast instead of snackbar here since the activity will exit.
            if (!geopermission.shouldShowRequestPermissionRationale(this)) {
                // Permission denied with checking "Do not ask again".
                geopermission.launchPermissionSettings(this)
            }
            finish()
        }
    }

}