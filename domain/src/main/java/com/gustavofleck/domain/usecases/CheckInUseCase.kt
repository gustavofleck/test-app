package com.gustavofleck.domain.usecases

import com.gustavofleck.domain.models.CheckInResult
import com.gustavofleck.domain.repository.EventCheckInRepository
import kotlinx.coroutines.flow.Flow

class CheckInUseCase(
    private val checkInRepository: EventCheckInRepository
) {

    operator fun invoke(eventId: String, name: String, email: String): Flow<CheckInResult> {
        return checkInRepository.checkIn(eventId, name, email)
    }
}