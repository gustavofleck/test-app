package com.gustavofleck.data.sources

import com.gustavofleck.data.api.EventsService
import com.gustavofleck.data.mappers.EventListMapper
import com.gustavofleck.domain.models.Event

class EventListDataSource(
    private val service: EventsService,
    private val mapper: EventListMapper
) {

    suspend fun eventList(): List<Event>{
        val response = service.events()
        return mapper.map(response)
    }

}