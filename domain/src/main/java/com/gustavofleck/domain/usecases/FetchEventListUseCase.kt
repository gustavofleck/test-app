package com.gustavofleck.domain.usecases

import com.gustavofleck.domain.repository.EventListRepository

class FetchEventListUseCase(
    private val repository: EventListRepository
) {

    operator fun invoke() = repository.eventList()
}