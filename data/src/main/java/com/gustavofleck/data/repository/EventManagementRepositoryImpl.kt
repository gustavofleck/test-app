package com.gustavofleck.data.repository

import com.gustavofleck.data.errors.handler.EventsErrorHandler
import com.gustavofleck.data.sources.EventDetailsDataSource
import com.gustavofleck.domain.models.Event
import com.gustavofleck.domain.repository.EventManagementRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class EventManagementRepositoryImpl(
    private val dataSource: EventDetailsDataSource,
    private val errorHandler: EventsErrorHandler
) : EventManagementRepository {
    override fun eventDetails(eventId: String): Flow<Event> {
        return flow { emit(dataSource.eventDetails(eventId)) }.catch { throw errorHandler.handle(it) }
    }

    override fun eventCheckIn(eventId: String, email: String, name: String): Flow<Unit> {
        TODO("Not yet implemented")
    }

}