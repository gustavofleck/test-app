package com.gustavofleck.domain.models

data class Event(
    val people: List<String>,
    val date: String,
    val description: String,
    val image: String,
    val longitude: Double,
    val latitude: Double,
    val price: Float,
    val title: String,
    val id: String
)

fun Event.simplify() = SimplifiedEvent(title, image, date, price, id)
