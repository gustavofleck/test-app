package com.gustavofleck.domain.repository

import com.gustavofleck.domain.models.CheckInResult
import kotlinx.coroutines.flow.Flow

interface EventCheckInRepository {

    fun checkIn(eventId: String, name: String, email: String): Flow<CheckInResult>
}