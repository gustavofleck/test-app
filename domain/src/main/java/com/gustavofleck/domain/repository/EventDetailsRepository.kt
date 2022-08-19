package com.gustavofleck.domain.repository

import com.gustavofleck.domain.models.Event
import kotlinx.coroutines.flow.Flow

interface EventDetailsRepository {

    fun eventDetails(eventId: String): Flow<Event>
}