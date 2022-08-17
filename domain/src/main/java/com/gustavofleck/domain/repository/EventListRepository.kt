package com.gustavofleck.domain.repository

import com.gustavofleck.domain.models.Event
import kotlinx.coroutines.flow.Flow

interface EventListRepository {

    fun eventList(): Flow<List<Event>>
}