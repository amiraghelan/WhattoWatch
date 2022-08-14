package com.example.whattowatch.adapter

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.whattowatch.R
import com.example.whattowatch.adapter.MovieAdapter.ViewHolder
import com.example.whattowatch.model.Movie

class MovieAdapter(
    private val context: Context,
    private val movies: List<Movie>,
    val onItemClick: (Movie) -> Unit
) : RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies[position]
        holder.tvTitle.text = movie.title
        holder.tvReleaseDate.text = movie.releaseDate
        Glide.with(context).load(movie.posterSmallPath).into(holder.imgPoster)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgPoster: ImageView = itemView.findViewById(R.id.img_poster)
        val tvTitle: TextView = itemView.findViewById(R.id.tv_title)
        val tvReleaseDate: TextView = itemView.findViewById(R.id.tv_release_date)

        init {
            itemView.setOnClickListener{
                onItemClick(movies[adapterPosition])
            }
        }
    }

}