package com.example.movietime.navigation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movietime.MoviesAdapter
import com.example.movietime.R
import com.example.movietime.dto.DataMovies
import com.example.movietime.retrofit.MovieRepository

class HomeFragment : Fragment() {

    private lateinit var popularMovies: RecyclerView
    private lateinit var popularMoviesAdapter: MoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        initRecyclerview(view)
        MovieRepository.getPopularMovies(
            onSuccess = ::onPopularMovieFetched,
            onError = ::onError
        )
        return view
    }

    private fun initRecyclerview(view: View) {
        popularMovies = view.findViewById(R.id.recyclerView)
        popularMovies.layoutManager = LinearLayoutManager(activity)
        popularMoviesAdapter = MoviesAdapter(listOf())
        popularMovies.adapter = popularMoviesAdapter
    }

    private fun onPopularMovieFetched(movies: List<DataMovies>) {
        popularMoviesAdapter.updateMovies(movies)
    }

    private fun onError() {
        Toast.makeText(context, "Please check your internet connection!", Toast.LENGTH_LONG).show()
    }


}