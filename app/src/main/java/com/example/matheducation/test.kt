package com.example.matheducation

import android.content.Intent
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.matheducation.databinding.ActivityTestBinding
import kotlin.random.Random

class test : AppCompatActivity() {

    private lateinit var binding : ActivityTestBinding
    var question = randquestion()
    var start = 0
    var correct = ""
    var isselect = false
    var ans = false
    var questiondata = dataquestion()
    var score = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getStringExtra("id").toString()

        correct = correctanswer(question[start].toString())
        binding.question.text = questiondata[start]
        binding.qa.text = start.toString()
        adddatatobutton(question[start].toString())

        binding.a1.setOnClickListener {
            isselect = true
            checkanswer(binding.a1)
        }

        binding.a2.setOnClickListener {
            isselect = true
            checkanswer(binding.a2)
        }

        binding.a3.setOnClickListener {
            isselect = true
            checkanswer(binding.a3)
        }

        binding.a4.setOnClickListener {
            isselect = true
            checkanswer(binding.a4)
        }

        binding.a5.setOnClickListener {
            isselect = true
            checkanswer(binding.a5)
        }

        binding.a6.setOnClickListener {
            isselect = true
            checkanswer(binding.a6)
        }

        binding.a7.setOnClickListener {
            isselect = true
            checkanswer(binding.a7)
        }

        binding.submit.setOnClickListener {
            if (isselect) {
                if (start < 9) {
                    start++
                    isselect = false
                    addscore()
                    adddatatobutton(question[start].toString())
                    correct = correctanswer(question[start].toString())
                    binding.qa.text = start.toString()
                    binding.question.text = questiondata[start]
                } else {
                    val intent = Intent(this, scorescreen::class.java)
                    intent.putExtra("id",id.toString())
                    intent.putExtra("sc",score.toString() )
                    startActivity(intent)
                    finish()
                }
            }else{
                Toast.makeText(this,"select a option",Toast.LENGTH_SHORT).show()
            }
        }


    }

    fun randquestion():IntArray{
        val random = Random(System.currentTimeMillis())
        val numbers = mutableListOf<Int>()
        while (numbers.size < 11) {
            val randomNumber = random.nextInt(0, 20)
            if (!numbers.contains(randomNumber)) {
                numbers.add(randomNumber)
            }
        }
        return numbers.toIntArray()
    }

    fun randmcq():IntArray{
        val random = Random(System.currentTimeMillis())
        val numbers = mutableListOf<Int>()
        while (numbers.size < 7) {
            val randomNumber = random.nextInt(0, 7)
            if (!numbers.contains(randomNumber)) {
                numbers.add(randomNumber)
            }
        }
        return numbers.toIntArray()
    }


    fun dataquestion():ArrayList<String>{
        val fq = ArrayList<String>()
        for (number in question){
            val cursor = splashscreen.dbHelper.database?.rawQuery("SELECT * from TQuestion where Id == '$number'", null)
            if (cursor != null && cursor.moveToFirst()) {
                fq.add( cursor.getString(cursor.getColumnIndexOrThrow("QuestionText")) )
            }
        }
        return fq
    }

    fun datamcq(id:String):ArrayList<String>{
        val fq = ArrayList<String>()
        val cursor = splashscreen.dbHelper.database?.rawQuery("SELECT * from TAnswer where QuestionId == '$id'", null)
        if (cursor != null && cursor.moveToFirst()) {
            do {
                val answerText = cursor.getString(cursor.getColumnIndexOrThrow("AnswerText"))
                fq.add(answerText)
            } while (cursor.moveToNext())
        }
        cursor?.close()
        return fq
    }

    fun adddatatobutton(id: String){
        val mcq = randmcq()
        val mcqdata = datamcq(id)
        try {
            for (n in mcq){
                if(n == 0){
                    binding.a1.text = mcqdata[mcq[n]]
                }else if(n == 1){
                    binding.a2.text = mcqdata[mcq[n]]
                }else if(n == 2){
                    binding.a3.text = mcqdata[mcq[n]]
                }else if(n == 3){
                    binding.a4.text = mcqdata[mcq[n]]
                }else if(n == 4){
                    binding.a5.text = mcqdata[mcq[n]]
                }else if(n == 5){
                    binding.a6.text = mcqdata[mcq[n]]
                }else if(n == 6){
                    binding.a7.text = mcqdata[mcq[n]]
                }
            }
        }catch (e:Exception){
            Toast.makeText(this,"failed to read data select any option and continue",Toast.LENGTH_SHORT).show()
        }

    }

    fun correctanswer(id:String):String{
        var s = ""
        val cursor = splashscreen.dbHelper.database?.rawQuery("SELECT * from TAnswer where QuestionId == '$id' and IsCorrect == 1", null)
        if (cursor != null && cursor.moveToFirst()) {
            s =  cursor.getString(cursor.getColumnIndexOrThrow("AnswerText"))
        }
        return s
    }

    fun checkanswer(btn:Button){
        ans = btn.text.toString() == correct
    }

    fun addscore(){
        if (ans){
            score = score+1
        }
    }

}