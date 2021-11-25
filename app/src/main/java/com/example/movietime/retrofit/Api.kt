package com.example.movietime.retrofit

import retrofit2.Call
import retrofit2.http.*

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
    ):Call<GetMovieResponse>

    @GET("movie/upcoming")
    fun getUpcomingMovies(
        @Query("api_key") apiKey: String = "37d2b486678462f6924f47bf9d520ffe",
        @Query("page") page: Int
    ):Call<GetMovieResponse>

//    @GET("search/movie")
//    fun getSearchMovies(
//        @Query("api_key") apiKey: String,
//        @Query("page") page: Int,
//        @Query("query") queryValue: String
//        ):Call<GetMovieResponse>

    @GET("authentication/guest_session/new")
    fun getGuestSession(
        @Query("api_key") apiKey: String = "37d2b486678462f6924f47bf9d520ffe"
    ):Call<GetGuestSessionResponse>

    // https://api.themoviedb.org/3/movie/550/rating?api_key=37d2b486678462f6924f47bf9d520ffe&guest_session_id=f82aeec76956ec81bbfeb27504ce6a04

    //@Headers("Content-Type:application/json;charset=utf-8")
    @POST("movie/{movie_id}/rating")
    fun postRating(
        @Header("Content-Type") contentType: String,
        @Body value: Float,
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = "37d2b486678462f6924f47bf9d520ffe",
        @Query("guest_session_id") guestSessionId: String = "9d9334c0f45867e31876e528abdd095d"
    ):Call<PostRatingResponse>

}