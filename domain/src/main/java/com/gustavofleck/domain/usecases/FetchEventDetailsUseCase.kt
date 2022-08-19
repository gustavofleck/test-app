package com.gustavofleck.domain.usecases

import com.gustavofleck.domain.models.Event
import com.gustavofleck.domain.repository.EventManagementRepository
import kotlinx.coroutines.flow.Flow

class FetchEventDetailsUseCase(
    private val eventManagementRepository: EventManagementRepository
) {

    operator fun invoke(eventId: String): Flow<Event> {
        return eventManagementRepository.eventDetails(eventId)
    }
}