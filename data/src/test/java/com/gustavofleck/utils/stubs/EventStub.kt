package com.gustavofleck.utils.stubs

import com.gustavofleck.data.models.responses.EventResponse
import com.gustavofleck.domain.models.Event

fun createEventResponse() = EventResponse(
    people = listOf(),
    date = 1232123123,
    description = "Test description response",
    image = "https://imageurl.teste.response",
    longitude = -65.132132132,
    latitude = -25.465486,
    price = 32.99f,
    title = "Test Title Response",
    id = "121"
)

fun createEvent() = Event(
    people = listOf(),
    date = "12/12/2022",
    description = "Test description response",
    image = "https://imageurl.teste.response",
    longitude = -65.132132132,
    latitude = -25.465486,
    price = 32.99f,
    title = "Test Title Response",
    id = "121"
)