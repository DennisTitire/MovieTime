package com.example.movietime

import android.provider.ContactsContract
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movietime.dto.DataMovies
import com.example.movietime.retrofit.MoviesRepository

class MoviesViewModel : ViewModel() {

    init {
        getPopularMovies()
        getTopRatedMovies()
        getUpcomingMovies()
    }

    private val _popularMovies = MutableLiveData<List<DataMovies>>()

    val popularMovies: LiveData<List<DataMovies>>
        get() = _popularMovies

    private val _topRatedMovies = MutableLiveData<List<DataMovies>>()

    val topRatedMovies: LiveData<List<DataMovies>>
        get() = _topRatedMovies

    private val _upcomingMovies = MutableLiveData<List<DataMovies>>()

    val upcomingMovies: LiveData<List<DataMovies>>
        get() = _upcomingMovies

    private val _error = MutableLiveData<Boolean>()

    val error: LiveData<Boolean>
        get() = _error


    private fun onError() {
        _error.value = true
    }

    private fun onPopularMoviesFetched(movies: List<DataMovies>) {
        _popularMovies.value = movies
    }

    private fun onTopRatedMoviesFetched(movies: List<DataMovies>) {
        _topRatedMovies.value = movies
    }

    private fun onUpcomingMoviesFetched(movies: List<DataMovies>) {
        _upcomingMovies.value = movies
    }

    fun getPopularMovies(page: Int = 1) {
        MoviesRepository.getPopularMovies(
            page,
            ::onPopularMoviesFetched,
            ::onError
        )
    }

    fun getTopRatedMovies(page: Int = 1) {
        MoviesRepository.getTopRatedMovies(
            page,
            ::onTopRatedMoviesFetched,
            ::onError
        )
    }
    fun getUpcomingMovies(page: Int = 1) {
        MoviesRepository.getUpcomingMovies(
            page,
            ::onUpcomingMoviesFetched,
            ::onError
        )
    }

}