package com.example.matheducation.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.matheducation.R
import com.example.matheducation.model.viewscoremodel
import com.example.matheducation.viewscore

class viewscoreadapter(private val itemList: ArrayList<viewscoremodel>,private val context: Context,private val id:String)
    : RecyclerView.Adapter<viewscoreadapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.viewscore, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = itemList[position]

        if(id=="-2"){
            holder.score.text = "name : " + item.score
            holder.date.text = "pass : " + item.date
        } else if(id == "-3"){
            val parts = item.score.split(",")
            holder.score.text = "username : " + parts[0]+"      score : "+parts[1]
            holder.date.text = "Date : " + item.date
        }else if(id=="-4"){
            holder.score.text = "name : " + item.score
            holder.date.text = "pass : " + item.date
            holder.itemView.setOnClickListener {
                val intent = Intent(context, viewscore::class.java)
                intent.putExtra("id", item.id)
                intent.putExtra("name", item.score)
                context.startActivity(intent)
            }
        }
        else {
            holder.score.text = "Score : " + item.score
            holder.date.text = "Date : " + item.date
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val score: TextView = itemView.findViewById(R.id.score)
        val date: TextView = itemView.findViewById(R.id.date)
    }


}
