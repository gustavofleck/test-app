package com.gustavofleck.data.repository

import com.gustavofleck.data.errors.handler.EventsErrorHandler
import com.gustavofleck.data.sources.EventListDataSource
import com.gustavofleck.domain.models.Event
import com.gustavofleck.domain.repository.EventListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class EventListRepositoryImpl(
    private val dataSource: EventListDataSource,
    private val errorHandler: EventsErrorHandler
) : EventListRepository {
    override fun eventList(): Flow<List<Event>> {
        return flow { emit(dataSource.eventList()) }.catch { throw errorHandler.handle(it) }
    }
}