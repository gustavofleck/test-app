package com.gustavofleck.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gustavofleck.domain.exceptions.ConnectionException
import com.gustavofleck.domain.models.SimplifiedEvent
import com.gustavofleck.domain.usecases.FetchEventListUseCase
import com.gustavofleck.presentation.viewstates.EventListViewState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class EventListViewModel(
    private val fetchEventList: FetchEventListUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): ViewModel() {

    private val _viewStateLiveData = MutableLiveData<EventListViewState>()
    val viewStateLiveData: LiveData<EventListViewState> = _viewStateLiveData

    fun eventList() {
        viewModelScope.launch {
            fetchEventList()
                .flowOn(dispatcher)
                .onStart { handleLoadingState() }
                .catch { error -> handleErrorState(error) }
                .collect { events -> handleSuccessState(events) }
        }
    }

    private fun handleSuccessState(events: List<SimplifiedEvent>) {
        _viewStateLiveData.value = EventListViewState.Success(events)
    }

    private fun handleErrorState(error: Throwable) {
        _viewStateLiveData.value = if (error is ConnectionException) {
            EventListViewState.ConnectionError
        } else {
            EventListViewState.GenericError
        }
    }

    private fun handleLoadingState() {
        _viewStateLiveData.value = EventListViewState.Loading
    }
}