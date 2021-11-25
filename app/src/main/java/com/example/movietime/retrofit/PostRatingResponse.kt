package com.example.movietime.retrofit

import com.google.gson.annotations.SerializedName

data class PostRatingResponse(
    @SerializedName("status_code") val statusCodePost: Int,
    @SerializedName("status_message") val statusMessagePost: String,
    @SerializedName("value") val valueRating: Float
)
