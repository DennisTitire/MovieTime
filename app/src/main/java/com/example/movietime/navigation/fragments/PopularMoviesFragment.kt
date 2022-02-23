package com.example.movietime.navigation.fragments

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movietime.*
import com.example.movietime.dto.DataMovies
import com.example.movietime.retrofit.MoviesRepository
import com.example.movietime.retrofit.RatingResponse
import kotlinx.android.synthetic.main.activity_registration.*
import kotlinx.android.synthetic.main.fragment_popular_movies.*

const val MOVIE_ID = "extra_movie_id"

class PopularMoviesFragment : Fragment() {

    //PopularMovies
    private lateinit var popularMovies: RecyclerView
    private lateinit var popularMoviesAdapter: MoviesAdapter
    private lateinit var popularMoviesLayoutMgr: LinearLayoutManager
    private var popularMoviesPage = 1

    //TopRatedMovies
    private lateinit var topRatedMovies: RecyclerView
    private lateinit var topRatedMoviesAdapter: MoviesAdapter
    private lateinit var topRatedMoviesLayoutMgr: LinearLayoutManager
    private var topRatedMoviesPage = 1

    //UpcomingMovies
    private lateinit var upcomingMovies: RecyclerView
    private lateinit var upcomingMoviesAdapter: MoviesAdapter
    private lateinit var upcomingMoviesLayoutMgr: LinearLayoutManager
    private var upcomingMoviesPage = 1

    lateinit var viewModel: MoviesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this.requireActivity())[MoviesViewModel::class.java]
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_popular_movies, container, false)
        initRecyclerviewPopularMovies(view)
        initRecyclerviewTopRatedMovies(view)
        initRecyclerviewUpcomingMovies(view)

        //getGuestSession()
       // postRating()
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.popularMovies.observe(viewLifecycleOwner, Observer { movies ->
            popularMoviesAdapter.appendMovies(movies)
            attachPopularMoviesOnScrollListener()
        })

        viewModel.topRatedMovies.observe(viewLifecycleOwner, Observer { movies ->
            topRatedMoviesAdapter.appendMovies(movies)
            attachTopRatedMoviesOnScrollListener()
        })

        viewModel.upcomingMovies.observe(viewLifecycleOwner, Observer { movies ->
            upcomingMoviesAdapter.appendMovies(movies)
            attachUpcomingMoviesOnScrollListener()
        })

        viewModel.error.observe(viewLifecycleOwner, Observer { onError() })

    }

    //GuestSession

//    private fun getGuestSession() {
//        GuestSessionResponse.getGuestResponse()
//    }

    private fun postRating() {
        RatingResponse.postResponse()
    }

    // PopularMovies

    private fun initRecyclerviewPopularMovies(view: View) {
        popularMovies = view.findViewById(R.id.recyclerView)
        popularMoviesLayoutMgr = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        popularMovies.layoutManager = popularMoviesLayoutMgr
        popularMoviesAdapter = MoviesAdapter(mutableListOf()) { movie -> showMovieDetails(movie) }
        popularMovies.adapter = popularMoviesAdapter
    }

    private fun initRecyclerviewTopRatedMovies(view: View) {
        topRatedMovies = view.findViewById(R.id.recyclerViewTopRatedMovies)
        topRatedMoviesLayoutMgr = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        topRatedMovies.layoutManager = topRatedMoviesLayoutMgr
        topRatedMoviesAdapter = MoviesAdapter(mutableListOf()) { movie -> showMovieDetails(movie) }
        topRatedMovies.adapter = topRatedMoviesAdapter
    }

    private fun initRecyclerviewUpcomingMovies(view: View) {
        upcomingMovies = view.findViewById(R.id.recyclerViewUpcomingMovies)
        upcomingMoviesLayoutMgr = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        upcomingMovies.layoutManager = upcomingMoviesLayoutMgr
        upcomingMoviesAdapter = MoviesAdapter(mutableListOf()) { movie -> showMovieDetails(movie) }
        upcomingMovies.adapter = upcomingMoviesAdapter
    }

    private fun attachPopularMoviesOnScrollListener() {
        popularMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = popularMoviesLayoutMgr.itemCount
                val visibleItemCount = popularMoviesLayoutMgr.childCount
                val firstVisibleItem = popularMoviesLayoutMgr.findFirstVisibleItemPosition()

                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                    popularMovies.removeOnScrollListener(this)
                    popularMoviesPage++
                    viewModel.getPopularMovies(popularMoviesPage)
                }
            }
        })
    }

    private fun attachTopRatedMoviesOnScrollListener() {
        topRatedMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = topRatedMoviesLayoutMgr.itemCount
                val visibleItemCount = topRatedMoviesLayoutMgr.childCount
                val firstVisibleItem = topRatedMoviesLayoutMgr.findFirstVisibleItemPosition()
                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                    topRatedMovies.removeOnScrollListener(this)
                    topRatedMoviesPage++
                    viewModel.getTopRatedMovies(topRatedMoviesPage)
                }
            }
        })
    }

    private fun attachUpcomingMoviesOnScrollListener() {
        upcomingMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = upcomingMoviesLayoutMgr.itemCount
                val visibleItemCount = upcomingMoviesLayoutMgr.childCount
                val firstVisibleItem = upcomingMoviesLayoutMgr.findFirstVisibleItemPosition()
                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                    upcomingMovies.removeOnScrollListener(this)
                    upcomingMoviesPage++
                    viewModel.getUpcomingMovies(upcomingMoviesPage)
                }
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun onError() {
        textPopularMovies.text = ""
        textRatedMovies.text = ""
        textUpcomingMovies.text = "You don' have internet connection!"
        val builder = AlertDialog.Builder(this.requireActivity())
        builder.setCancelable(true)
        builder.setTitle("Movie Time")
        builder.setMessage("Don't have access to internet, you can view the watch list!")
        builder.setPositiveButton("Continue") { dialog, i -> dialog.cancel() }
        builder.setNegativeButton("Close the app") {dialog, i -> activity?.finish() }
        builder.show()
        //Toast.makeText(context, "Don't have access to internet, you can view the watch list!", Toast.LENGTH_LONG).show()
    }

    // MovieDetails
    private fun showMovieDetails(movie: DataMovies) {
        val intent = Intent(activity, MovieDetails::class.java)
        intent.putExtra(MOVIE_ID, movie.id)
        intent.putExtra(MOVIE_BACKDROP, movie.backdropPath)
        intent.putExtra(MOVIE_POSTER, movie.posterPath)
        intent.putExtra(MOVIE_TITLE, movie.title)
        intent.putExtra(MOVIE_RATING, movie.rating)
        intent.putExtra(MOVIE_RELEASE_DATE, movie.releaseDate)
        intent.putExtra(MOVIE_OVERVIEW, movie.overview)
        startActivity(intent)
    }
}
