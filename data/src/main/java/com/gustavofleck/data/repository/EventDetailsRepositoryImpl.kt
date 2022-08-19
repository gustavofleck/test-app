package com.gustavofleck.data.repository

import com.gustavofleck.data.errors.handler.EventsErrorHandler
import com.gustavofleck.data.sources.EventDetailsDataSource
import com.gustavofleck.domain.models.Event
import com.gustavofleck.domain.repository.EventDetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class EventDetailsRepositoryImpl(
    private val dataSource: EventDetailsDataSource,
    private val errorHandler: EventsErrorHandler
) : EventDetailsRepository {
    override fun eventDetails(eventId: String): Flow<Event> {
        return flow { emit(dataSource.eventDetails(eventId)) }.catch { throw errorHandler.handle(it) }
    }
}