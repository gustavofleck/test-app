package com.gustavofleck.presentation.viewmodels

import androidx.lifecycle.Observer
import com.gustavofleck.domain.usecases.FetchEventListUseCase
import com.gustavofleck.presentation.utils.common.CoroutinesTestExtension
import com.gustavofleck.presentation.utils.common.InstantExecutorExtension
import com.gustavofleck.presentation.viewstates.EventListViewState
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(InstantExecutorExtension::class, CoroutinesTestExtension::class)
internal class EventListViewModelTest {

    private val viewStateObserverMock = mockk<Observer<EventListViewState>>(relaxed = true)
    private val useCaseMock = mockk<FetchEventListUseCase>(relaxed = true)
    private val viewModel = EventListViewModel(useCaseMock, Dispatchers.Unconfined)

    @BeforeEach
    fun setUp() {
        viewModel.viewStateLiveData.observeForever(viewStateObserverMock)
    }

    @Test
    fun `eventList Should set Loading state When a request begins`() {
        runTest {
            viewModel.eventList()

            verify {
                viewStateObserverMock.onChanged(EventListViewState.Loading)
            }
        }

    }

}