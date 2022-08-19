package com.gustavofleck.data.mappers

import com.gustavofleck.data.models.EventResponse
import com.gustavofleck.domain.models.Event
import java.text.SimpleDateFormat
import java.util.Locale

class EventListMapper(private val eventMapper: EventMapper) {

    fun map(response: List<EventResponse>): List<Event> {
        val eventList = mutableListOf<Event>()
        response.forEach { eventResponse ->
            eventList.add(eventMapper.map(eventResponse))
        }
        return eventList
    }
}