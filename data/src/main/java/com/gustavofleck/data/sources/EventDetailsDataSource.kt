package com.gustavofleck.data.sources

import com.gustavofleck.data.api.EventsService
import com.gustavofleck.data.mappers.EventMapper
import com.gustavofleck.domain.models.Event

class EventDetailsDataSource(
    private val service: EventsService,
    private val eventMapper: EventMapper
) {

    suspend fun eventDetails(eventId: String): Event {
        val response = service.event(eventId)
        return eventMapper.map(response)
    }

}