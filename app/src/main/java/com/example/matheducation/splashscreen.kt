package com.example.matheducation

import DatabaseHelper
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.matheducation.databinding.ActivitySplashscreenBinding

class splashscreen : AppCompatActivity() {

    private lateinit var binding : ActivitySplashscreenBinding

    companion object{
        lateinit var dbHelper :DatabaseHelper;
        lateinit var sharedPreferences : SharedPreferences
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashscreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        splashscreen.dbHelper = DatabaseHelper(this, sharedPreferences)
//        dbHelper.openDatabase()

        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)


    }
}