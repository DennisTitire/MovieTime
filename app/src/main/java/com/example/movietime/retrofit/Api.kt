package com.example.movietime.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String = "37d2b486678462f6924f47bf9d520ffe",
        @Query("page") page: Int
    ): Call<GetMovieResponse>

    @GET("movie/top_rated")
    fun getTopRatedMovies(
        @Query("api_key") apiKey: String = "37d2b486678462f6924f47bf9d520ffe",
        @Query("page") page: Int
    ): Call<GetMovieResponse>

    @GET("movie/upcoming")
    fun getUpcomingMovies(
        @Query("api_key") apiKey: String = "37d2b486678462f6924f47bf9d520ffe",
        @Query("page") page: Int
    ): Call<GetMovieResponse>

    @GET("search/movie")
    fun getSearchMovies(
        @Query("api_key") apiKey: String = "37d2b486678462f6924f47bf9d520ffe",
        @Query("page") page: Int,
        @Query("query") queryValue: String
    ): Call<GetSearchMovie>

}