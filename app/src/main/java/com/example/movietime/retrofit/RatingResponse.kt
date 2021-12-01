package com.example.movietime.retrofit

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RatingResponse {

    private const val BASE_URL = "https://api.themoviedb.org/3/"
    private val api: Api

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(Api::class.java)
    }

    fun postResponse() {
        api.postRating("application/json;charset=utf-8",5.5f, 550).enqueue(object : Callback<PostRatingResponse> {
            override fun onResponse(call: Call<PostRatingResponse>, response: Response<PostRatingResponse>) {
                Log.d("RatingResponse", "The message is: $response")
            }

            override fun onFailure(call: Call<PostRatingResponse>, t: Throwable) {
                Log.d("RatingResponse", "The error message is: ${t.message}")
            }

        })
    }

}