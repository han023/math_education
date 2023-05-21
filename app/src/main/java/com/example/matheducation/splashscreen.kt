package com.example.matheducation

import DatabaseHelper
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.matheducation.databinding.ActivitySplashscreenBinding

class splashscreen : AppCompatActivity() {

    private lateinit var binding : ActivitySplashscreenBinding

    companion object{
        lateinit var dbHelper :DatabaseHelper;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashscreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        splashscreen.dbHelper = DatabaseHelper(this)
        dbHelper.openDatabase()

        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)


    }
}