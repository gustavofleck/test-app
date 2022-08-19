package com.gustavofleck.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gustavofleck.data.errors.exceptions.ConnectionException
import com.gustavofleck.domain.models.Event
import com.gustavofleck.domain.usecases.FetchEventDetailsUseCase
import com.gustavofleck.presentation.viewstates.EventManagementViewState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class EventManagementViewModel(
    private val fetchEventDetails: FetchEventDetailsUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): ViewModel() {

    private val _viewStateLiveData = MutableLiveData<EventManagementViewState>()
    val viewStateLiveData: LiveData<EventManagementViewState> = _viewStateLiveData

    fun eventDetails(eventId: String) {
        viewModelScope.launch {
            fetchEventDetails(eventId)
                .flowOn(dispatcher)
                .onStart { handleLoadingState() }
                .catch { error -> handleErrorState(error) }
                .collect { event -> handleSuccessState(event) }
        }
    }

    private fun handleSuccessState(event: Event) {
        _viewStateLiveData.value = EventManagementViewState.SuccessDetails(event)
    }

    private fun handleErrorState(error: Throwable) {
        _viewStateLiveData.value = if (error is ConnectionException) {
            EventManagementViewState.ConnectionError
        } else {
            EventManagementViewState.GenericError
        }
    }

    private fun handleLoadingState() {
        _viewStateLiveData.value = EventManagementViewState.Loading
    }
}