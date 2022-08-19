package com.gustavofleck.data.sources

import com.gustavofleck.data.api.EventsService
import com.gustavofleck.data.models.requests.CheckInRequest
import com.gustavofleck.data.models.responses.map
import com.gustavofleck.domain.models.CheckInResult

class EventCheckInDataSource(
    private val service: EventsService
) {

    suspend fun checkIn(request: CheckInRequest): CheckInResult {
        return service.checkIn(request).map()
    }

}