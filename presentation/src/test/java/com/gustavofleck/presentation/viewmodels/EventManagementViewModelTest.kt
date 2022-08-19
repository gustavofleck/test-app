package com.gustavofleck.presentation.viewmodels

import androidx.lifecycle.Observer
import com.gustavofleck.data.errors.exceptions.ConnectionException
import com.gustavofleck.domain.usecases.FetchEventDetailsUseCase
import com.gustavofleck.presentation.utils.common.CoroutinesTestExtension
import com.gustavofleck.presentation.utils.common.InstantExecutorExtension
import com.gustavofleck.presentation.utils.stubs.createEvent
import com.gustavofleck.presentation.viewstates.EventManagementViewState
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(InstantExecutorExtension::class, CoroutinesTestExtension::class)
internal class EventManagementViewModelTest {

    private val eventId = "121"
    private val viewStateObserverMock = mockk<Observer<EventManagementViewState>>(relaxed = true)
    private val useCaseMock = mockk<FetchEventDetailsUseCase>(relaxed = true)
    private val viewModel = EventManagementViewModel(useCaseMock, Dispatchers.Unconfined)

    @BeforeEach
    fun setUp() {
        viewModel.viewStateLiveData.observeForever(viewStateObserverMock)
    }

    @Test
    fun `eventDetails Should set Loading state When a request begins`() {
        runTest {
            viewModel.eventDetails(eventId)

            verify {
                viewStateObserverMock.onChanged(EventManagementViewState.Loading)
            }
        }

    }

    @Test
    fun `eventList Should set Success state When request is successfully completed`() {
        val expectedEventDetails = createEvent()
        every { useCaseMock.invoke(eventId) } returns flowOf(expectedEventDetails)
        runTest {
            viewModel.eventDetails(eventId)

            verify {
                viewStateObserverMock.onChanged(EventManagementViewState.SuccessDetails(expectedEventDetails))
            }
        }
    }

    @Test
    fun `eventList Should set ConnectionError state When has connection issues`() {
        every { useCaseMock.invoke(eventId) } returns flow { throw ConnectionException() }
        runTest {
            viewModel.eventDetails(eventId)

            verify {
                viewStateObserverMock.onChanged(EventManagementViewState.ConnectionError)
            }
        }
    }

    @Test
    fun `eventList Should set GenericError state When has generic issues`() {
        every { useCaseMock.invoke(eventId) } returns flow { throw Throwable() }
        runTest {
            viewModel.eventDetails(eventId)

            verify {
                viewStateObserverMock.onChanged(EventManagementViewState.GenericError)
            }
        }
    }

}