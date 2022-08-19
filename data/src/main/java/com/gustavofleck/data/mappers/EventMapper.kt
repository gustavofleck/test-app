package com.gustavofleck.data.mappers

import com.gustavofleck.data.models.EventResponse
import com.gustavofleck.domain.models.Event
import java.text.SimpleDateFormat
import java.util.Locale

class EventMapper(
    private val simpleDateFormat: SimpleDateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.US)
) {

    fun map(eventResponse: EventResponse): Event {
        return with(eventResponse) {
            Event(
                people = people.orEmpty(),
                date = simpleDateFormat.format(eventResponse.date).orEmpty(),
                description = description.orEmpty(),
                image = image.orEmpty(),
                longitude = longitude ?: 0.0,
                latitude = latitude ?: 0.0,
                price = price ?: 0.00f,
                title = title.orEmpty(),
                id = id.orEmpty()
            )
        }
    }

}