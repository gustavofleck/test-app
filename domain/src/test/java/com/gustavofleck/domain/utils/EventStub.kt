package com.gustavofleck.domain.utils

import com.gustavofleck.domain.models.Event

fun createEvent() = Event(
    people = listOf(),
    date = "12/12/2022",
    description = "Test description",
    image = "https://imageurl.teste",
    address = "Rua 123",
    price = 32.99f,
    title = "Test Title",
    id = 121
)