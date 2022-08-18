package com.gustavofleck.domain.models

data class SimplifiedEvent(
    val title: String,
    val image: String,
    val date: String,
    val longitude: Double,
    val latitude: Double,
    val price: Float,
    val id: String
)