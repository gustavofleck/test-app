package com.gustavofleck.domain.repository

import com.gustavofleck.domain.models.Event
import kotlinx.coroutines.flow.Flow

interface EventManagementRepository {

    fun eventDetails(eventId: String): Flow<Event>

    fun eventCheckIn(eventId: String, email: String, name: String): Flow<Unit>
}