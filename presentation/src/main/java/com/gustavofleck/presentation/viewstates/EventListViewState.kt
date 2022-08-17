package com.gustavofleck.presentation.viewstates

import com.gustavofleck.domain.models.SimplifiedEvent

sealed class EventListViewState {
    object Loading: EventListViewState()
    object GenericError: EventListViewState()
    object ConnectionError: EventListViewState()
    data class Success(val eventList: List<SimplifiedEvent>): EventListViewState()
}
