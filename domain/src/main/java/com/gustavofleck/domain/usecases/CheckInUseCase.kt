package com.gustavofleck.domain.usecases

import com.gustavofleck.domain.exceptions.InvalidDataException
import com.gustavofleck.domain.models.CheckInResult
import com.gustavofleck.domain.repository.EventCheckInRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CheckInUseCase(
    private val checkInRepository: EventCheckInRepository
) {

    operator fun invoke(eventId: String, name: String, email: String): Flow<CheckInResult> {
        return if (name.isEmpty() or email.isEmpty()) {
            flow { throw InvalidDataException() }
        } else {
            checkInRepository.checkIn(eventId, name, email)
        }
    }
}