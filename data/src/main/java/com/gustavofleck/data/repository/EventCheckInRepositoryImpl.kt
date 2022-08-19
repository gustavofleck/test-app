package com.gustavofleck.data.repository

import com.gustavofleck.data.errors.handler.EventsErrorHandler
import com.gustavofleck.data.models.requests.CheckInRequest
import com.gustavofleck.data.sources.EventCheckInDataSource
import com.gustavofleck.domain.models.CheckInResult
import com.gustavofleck.domain.repository.EventCheckInRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class EventCheckInRepositoryImpl(
    private val dataSource: EventCheckInDataSource,
    private val errorHandler: EventsErrorHandler
): EventCheckInRepository {
    override fun checkIn(eventId: String, name: String, email: String): Flow<CheckInResult> {
        val request = createCheckInRequest(eventId, name, email)
        return flow { emit(dataSource.checkIn(request)) }.catch { throw errorHandler.handle(it) }
    }

    private fun createCheckInRequest(eventId: String, name: String, email: String): CheckInRequest {
        return CheckInRequest(eventId, name, email)
    }
}