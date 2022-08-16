package com.gustavofleck.data.models

data class Event(
    val people: List<String>,
    val date: String,
    val description: String,
    val image: String,
    val address: String,
    val price: Float,
    val title: String,
    val id: Int
)
