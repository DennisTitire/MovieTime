package com.example.movietime.navigation.fragments.watchlist

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.movietime.*
import com.example.movietime.navigation.fragments.roomwatchlist.AppDatabase

class WatchListFragment : Fragment() {

    private lateinit var watchList: RecyclerView
    private lateinit var watchListAdapter: WatchListAdapter

    private val db: AppDatabase by lazy {
        Room.databaseBuilder(
            requireActivity().applicationContext,
            AppDatabase::class.java,
            "movies.db"
        ).allowMainThreadQueries().build()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_watch_list, container, false)
        initWatchList(view)
        getWatchList()

        return view
    }

    private fun initWatchList(view: View) {
        watchList = view.findViewById(R.id.watchList)
        watchList.layoutManager = GridLayoutManager(context, 1)
        watchListAdapter = WatchListAdapter(listOf()) { showMovieDetails(it) }
        watchList.adapter = watchListAdapter
    }

    override fun onHiddenChanged(hidden: Boolean) {
        if (!hidden) {
            getWatchList()
        }
    }

    override fun onResume() {
        super.onResume()
        getWatchList()
    }

    private fun getWatchList() {
        val movies = db.movieDao().getAll()
        val watchList = mutableListOf<WatchList>()
        watchList.addAll(
            movies.map { movie ->
                WatchList(
                    movie.id, movie.title, movie.overview, movie.posterPath, movie.backdropPath,
                    movie.rating, movie.releaseDate, WatchListType.MovieType
                )
            }
        )
        watchListAdapter.updateItems(watchList)
    }

    private fun showMovieDetails(item: WatchList) {
        val intent = Intent(activity, MovieDetails::class.java)
        intent.putExtra(MOVIE_ID, item.id)
        intent.putExtra(MOVIE_BACKDROP, item.backdropPath)
        intent.putExtra(MOVIE_POSTER, item.posterPath)
        intent.putExtra(MOVIE_TITLE, item.title)
        intent.putExtra(MOVIE_RATING, item.rating)
        intent.putExtra(MOVIE_RELEASE_DATE, item.releaseDate)
        intent.putExtra(MOVIE_OVERVIEW, item.overview)
        startActivity(intent)
    }


}