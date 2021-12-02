package com.example.movietime.retrofit

import com.example.movietime.dto.DataMovies
import com.google.gson.annotations.SerializedName

data class GetSearchMovie(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val movies: List<DataMovies>,
    @SerializedName("total_pages") val pages: Int,
    @SerializedName("query") val queryValue: String
)
