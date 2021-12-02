package com.example.movietime.retrofit

import android.util.Log
import com.example.movietime.dto.DataMovies
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SearchRepository {

    private const val BASE_URL = "https://api.themoviedb.org/3/"
    private val api: Api

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(Api::class.java)
    }
        fun getSearchMovies(page: Int = 1, queryValue: String, onSuccess: (movies: List<DataMovies>) -> Unit, onError: () -> Unit) {
            api.getSearchMovies(page = page, queryValue = queryValue).enqueue(object : Callback<GetSearchMovie> {
            override fun onResponse(call: Call<GetSearchMovie>, response: Response<GetSearchMovie>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    Log.d("Search", "The search result is: ${response.body()}")
                    if (responseBody != null) {
                        onSuccess.invoke(responseBody.movies)
                    } else {
                        onError.invoke()
                    }
                } else {
                    onError.invoke()
                }
            }
            override fun onFailure(call: Call<GetSearchMovie>, t: Throwable) {
                onError.invoke()
            }
        })
    }

}