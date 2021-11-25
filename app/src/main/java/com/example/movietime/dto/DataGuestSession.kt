package com.example.movietime.dto

data class DataGuestSession(
    var success: Boolean,
    val guestSessionId: String,
    val expiresAt: String
)
