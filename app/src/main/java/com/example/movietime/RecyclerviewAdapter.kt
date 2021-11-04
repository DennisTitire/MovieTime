package com.example.movietime

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerviewAdapter(private val listData: List<DataMovies>) : RecyclerView.Adapter<RecyclerviewAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerviewAdapter.MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recyclerview, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerviewAdapter.MyViewHolder, position: Int) {
        holder.titleTextView.text = listData.get(position).title
        holder.genreTextView.text = listData.get(position).genre
        holder.directorTextView.text = listData.get(position).director
        holder.descriptionTextView.text = listData.get(position).description
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var titleTextView: TextView = view.findViewById(R.id.itemTitle)
        var genreTextView: TextView = view.findViewById(R.id.itemGenre)
        var directorTextView: TextView = view.findViewById(R.id.itemDirector)
        var descriptionTextView: TextView = view.findViewById(R.id.itemDescription)
    }
}