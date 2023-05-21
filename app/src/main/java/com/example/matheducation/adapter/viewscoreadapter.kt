package com.example.matheducation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.matheducation.R
import com.example.matheducation.model.viewscoremodel

class viewscoreadapter(private val itemList: ArrayList<viewscoremodel>,private val id:String)
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
