package com.gustavofleck.data.mappers

import com.gustavofleck.data.models.responses.EventResponse
import com.gustavofleck.domain.models.Event

class EventListMapper(private val eventMapper: EventMapper) {

    fun map(response: List<EventResponse>): List<Event> {
        val eventList = mutableListOf<Event>()
        response.forEach { eventResponse ->
            eventList.add(eventMapper.map(eventResponse))
        }
        return eventList
    }
}