package com.gustavofleck.data.models.requests

import com.google.gson.annotations.SerializedName

data class CheckInRequest(
    @SerializedName("eventId") val eventId: String,
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String
)
