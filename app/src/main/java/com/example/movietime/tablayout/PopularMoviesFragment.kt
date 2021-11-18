package com.example.movietime.tablayout

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
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_popular_movies.*

class PopularMoviesFragment: Fragment() {

    private lateinit var popularMovies: RecyclerView
    private lateinit var popularMoviesAdapter: MoviesAdapter
    private lateinit var pagerAdapter: PagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_popular_movies, container, false)
        initRecyclerview(view)
        MovieRepository.getPopularMovies(
            onSuccess = ::onPopularMovieFetched,
            onError = ::onError
        )
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //setUpTabBar()
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

    private fun setUpTabBar() {
        pagerAdapter = PagerAdapter(parentFragmentManager, lifecycle)
        viewPager.adapter = pagerAdapter

        TabLayoutMediator(tabLayout, viewPager, TabLayoutMediator.TabConfigurationStrategy{tab, position ->
            when(position) {
                0 -> tab.text = "Popular"
                1 -> tab.text = "Top Rated"
                else -> tab.text = "Upcoming"
            }
        }).attach()
    }

}