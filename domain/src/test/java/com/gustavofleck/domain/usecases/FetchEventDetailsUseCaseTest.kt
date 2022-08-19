package com.gustavofleck.domain.usecases

import app.cash.turbine.test
import com.gustavofleck.domain.repository.EventDetailsRepository
import com.gustavofleck.domain.utils.createEvent
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class FetchEventDetailsUseCaseTest {

    private val eventId = "121"
    private val repositoryMock = mockk<EventDetailsRepository>(relaxed = true)
    private val useCase = FetchEventDetailsUseCase(repositoryMock)

    @Test
    fun `invoke Should get from repository a Flow with a Event`() {
        val expectedEvent = createEvent()
        every { repositoryMock.eventDetails(eventId) } returns flowOf(expectedEvent)

        val result = useCase.invoke(eventId)

        runBlocking {
            result.test {
                Assertions.assertEquals(expectedEvent, awaitItem())
                awaitComplete()
            }
        }
    }

    @Test
    fun `invoke Should get from repository Flow throws a Throwable`() {
        val expectedThrowable = Throwable()
        every { repositoryMock.eventDetails(eventId) } returns flow { throw expectedThrowable }

        val result = useCase.invoke(eventId)

        runBlocking {
            result.test {
                Assertions.assertEquals(expectedThrowable, awaitError())
            }
        }
    }

}