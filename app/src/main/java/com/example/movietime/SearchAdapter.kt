package com.example.movietime

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movietime.dto.DataMovies

class SearchAdapter(
    private var movies: MutableList<DataMovies>,
    private val onMovieClick: (movie: DataMovies) -> Unit
) : RecyclerView.Adapter<SearchAdapter.SearchAdapterHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchAdapterHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_watch_list, parent, false)
        return SearchAdapterHolder(view)
    }

    override fun onBindViewHolder(holder: SearchAdapterHolder, position: Int) {
        holder.bind(movies[position])
    }

    fun appendMovies(movies: List<DataMovies>) {
        this.movies.addAll(movies)
        notifyItemRangeInserted(this.movies.size, movies.size - 1)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    inner class SearchAdapterHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val poster: ImageView = itemView.findViewById(R.id.itemWatchListPoster)

        fun bind(movie: DataMovies) {
            Glide.with(itemView)
                .load("https://image.tmdb.org/t/p/w342${movie.posterPath}")
                .error(R.drawable.ic_image_not_found)
                .centerCrop()
                .into(poster)
            itemView.setOnClickListener { onMovieClick.invoke(movie) }
        }
    }

}