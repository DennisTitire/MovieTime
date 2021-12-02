package com.example.movietime

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movietime.MainActivity.Companion.searchTextValGlob
import com.example.movietime.dto.DataMovies
import com.example.movietime.navigation.fragments.PopularMoviesFragment
import com.example.movietime.retrofit.SearchRepository

class SearchFragment : Fragment() {

    private lateinit var searchMovies: RecyclerView
    private lateinit var searchMoviesAdapter: SearchAdapter
    private lateinit var searchMoviesLayoutMgr: LinearLayoutManager
    private var searchMoviesPage = 1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_search, container, false)
        initRecyclerviewPopularMovies(view)
        getSearchMovies()
        return view
    }

    private fun initRecyclerviewPopularMovies(view: View) {
        searchMovies = view.findViewById(R.id.searchList)
        searchMoviesLayoutMgr = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
        searchMovies.layoutManager = searchMoviesLayoutMgr
        searchMoviesAdapter = SearchAdapter(mutableListOf()) { movie -> showMovieDetails(movie) }
        searchMovies.adapter = searchMoviesAdapter
    }

    private fun onSearchMovieFetched(movies: List<DataMovies>) {
        searchMoviesAdapter.appendMovies(movies)

    }

    private fun onError() {
       val fragmentManager = (activity as FragmentActivity).supportFragmentManager
        val fragmentTransition = fragmentManager.beginTransaction()
        fragmentTransition.replace(R.id.frameLayout, PopularMoviesFragment()).commit()
    }

    private fun getSearchMovies() {
        SearchRepository.getSearchMovies(
            searchMoviesPage,
            searchTextValGlob,
            ::onSearchMovieFetched,
            ::onError
        )
    }

    private fun showMovieDetails(movie: DataMovies) {
        val intent = Intent(activity, MovieDetails::class.java)
        intent.putExtra(com.example.movietime.navigation.fragments.MOVIE_ID, movie.id)
        intent.putExtra(MOVIE_BACKDROP, movie.backdropPath)
        intent.putExtra(MOVIE_POSTER, movie.posterPath)
        intent.putExtra(MOVIE_TITLE, movie.title)
        intent.putExtra(MOVIE_RATING, movie.rating)
        intent.putExtra(MOVIE_RELEASE_DATE, movie.releaseDate)
        intent.putExtra(MOVIE_OVERVIEW, movie.overview)
        startActivity(intent)
    }

}