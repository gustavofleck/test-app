package com.gustavofleck.domain.usecases

import com.gustavofleck.domain.models.Event
import com.gustavofleck.domain.repository.EventDetailsRepository
import kotlinx.coroutines.flow.Flow

class FetchEventDetailsUseCase(
    private val eventDetailsRepository: EventDetailsRepository
) {

    operator fun invoke(eventId: String): Flow<Event> {
        return eventDetailsRepository.eventDetails(eventId)
    }
}