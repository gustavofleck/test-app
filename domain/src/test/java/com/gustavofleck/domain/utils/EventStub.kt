package com.gustavofleck.domain.utils

import com.gustavofleck.domain.models.Event

fun createEvent() = Event(
    people = listOf(),
    date = "12/12/2022",
    description = "Test description",
    image = "https://imageurl.teste",
    latitude = -61.12312,
    longitude = -30.4132,
    price = 32.99f,
    title = "Test Title",
    id = "121"
)