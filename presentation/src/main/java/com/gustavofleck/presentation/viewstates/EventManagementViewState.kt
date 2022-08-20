package com.gustavofleck.presentation.viewstates

import com.gustavofleck.domain.models.Event

sealed class EventManagementViewState {
    object Loading: EventManagementViewState()
    object GenericError: EventManagementViewState()
    object ConnectionError: EventManagementViewState()
    object InvalidDataError: EventManagementViewState()
    object SuccessCheckIn: EventManagementViewState()
    data class SuccessDetails(val event: Event): EventManagementViewState()

}
