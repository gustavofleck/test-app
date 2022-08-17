package com.gustavofleck.domain.usecases

import com.gustavofleck.domain.models.Event
import com.gustavofleck.domain.models.SimplifiedEvent
import com.gustavofleck.domain.models.simplify
import com.gustavofleck.domain.repository.EventListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FetchEventListUseCase(
    private val repository: EventListRepository
) {

    operator fun invoke(): Flow<List<SimplifiedEvent>> {
        return repository.eventList().map { eventList ->
            simplifyEventList(eventList)
        }
    }

    private fun simplifyEventList(eventList: List<Event>): List<SimplifiedEvent> {
        val eventItemList = mutableListOf<SimplifiedEvent>()
        eventList.forEach { event -> eventItemList.add(event.simplify()) }
        return eventItemList
    }
}