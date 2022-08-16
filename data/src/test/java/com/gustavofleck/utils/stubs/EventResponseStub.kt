package com.gustavofleck.utils.stubs

import com.gustavofleck.data.models.EventResponse

fun createEventResponse() = EventResponse(
    people = listOf(),
    date = 1232123123,
    description = "Test description response",
    image = "https://imageurl.teste.teste",
    longitude = -65.132132132,
    latitude = -25.465486,
    price = 32.99f,
    title = "Test Title",
    id = "121"
)