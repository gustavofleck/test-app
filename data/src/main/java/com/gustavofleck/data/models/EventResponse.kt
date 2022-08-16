package com.gustavofleck.data.models

import com.google.gson.annotations.SerializedName

data class EventResponse(
    @SerializedName("people") val people: List<String>?,
    @SerializedName("date") val date: Long?,
    @SerializedName("description") val description: String?,
    @SerializedName("image") val image: String?,
    @SerializedName("longitude") val longitude: Long?,
    @SerializedName("latitude") val latitude: Long?,
    @SerializedName("price") val price: Float?,
    @SerializedName("title") val title: String?,
    @SerializedName("id") val id: String?
)