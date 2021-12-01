package com.example.movietime.retrofit

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GuestSessionResponse {

    private const val BASE_URL = "https://api.themoviedb.org/3/"
    private val api: Api

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(Api::class.java)
    }

    fun getGuestResponse() {
        api.getGuestSession().enqueue(object : Callback<GetGuestSessionResponse> {
            override fun onResponse(call: Call<GetGuestSessionResponse>, response: Response<GetGuestSessionResponse>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        val guestSessionId = responseBody.guestSessionId
                        Log.d("Guest", guestSessionId)
                    }
                }
                Log.d("Guest", "The guest id is $response")
            }
            override fun onFailure(call: Call<GetGuestSessionResponse>, t: Throwable) {
                Log.d("Guest", t.message.toString())
            }
        })
    }
}