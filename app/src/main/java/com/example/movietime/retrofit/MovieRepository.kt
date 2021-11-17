package com.example.movietime.retrofit

import android.util.Log
import com.example.movietime.dto.DataMovies
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MovieRepository {

    val BASE_URL = "https://api.themoviedb.org/3/"
    private val api: Api

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(Api::class.java)
    }
    fun getPopularMovies(
        page: Int  = 1,
        onSuccess: (movies: List<DataMovies>) -> Unit,
        onError: () -> Unit
    ) {
        api.getPopularMovies(page = page).enqueue(object : Callback<GetMovieResponse> {
            override fun onResponse(
                call: Call<GetMovieResponse>,
                response: Response<GetMovieResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        onSuccess.invoke(responseBody.movies)
                    } else {
                        onError.invoke()
                    }
                }
            }
            override fun onFailure(call: Call<GetMovieResponse>, t: Throwable) {
                onError.invoke()
            }
        }
        )
    }
}