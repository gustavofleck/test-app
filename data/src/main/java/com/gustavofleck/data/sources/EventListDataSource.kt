package com.gustavofleck.data.sources

import com.gustavofleck.data.api.EventsService
import com.gustavofleck.data.mappers.EventMapper
import com.gustavofleck.domain.models.Event

class EventListDataSource(
    private val service: EventsService,
    private val mapper: EventMapper
) {

    suspend fun eventList(): List<Event>{
        val response = service.events()
        return mapper.map(response)
    }

}