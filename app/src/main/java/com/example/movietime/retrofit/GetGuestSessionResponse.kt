package com.example.movietime.retrofit

import com.google.gson.annotations.SerializedName

data class GetGuestSessionResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("guest_session_id") val guestSessionId: String,
    @SerializedName("expires_at") val expiresAt: String
)
