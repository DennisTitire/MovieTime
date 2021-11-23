package com.example.movietime

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movietime.dto.DataMovies

class MoviesAdapter(
    private var movies: MutableList<DataMovies>,
    private val onMovieClick: (movie: DataMovies) -> Unit
) : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recyclerview, parent, false)
        return MovieViewHolder(view);
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    fun appendMovies(movies: List<DataMovies>) {
        this.movies.addAll(movies)
        notifyItemRangeInserted(this.movies.size, movies.size - 1)
    }


    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val poster: ImageView = itemView.findViewById(R.id.itemMoviePoster)

        fun bind(movie: DataMovies) {
            Glide.with(itemView).load("https://image.tmdb.org/t/p/w342${movie.posterPath}")
                .centerCrop()
                .into(poster)
            itemView.setOnClickListener { onMovieClick.invoke(movie) }
        }
    }

}