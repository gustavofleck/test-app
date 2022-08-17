package com.gustavofleck.domain.usecases

import app.cash.turbine.test
import com.gustavofleck.domain.models.simplify
import com.gustavofleck.domain.repository.EventListRepository
import com.gustavofleck.domain.utils.createEvent
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class FetchEventListUseCaseTest {

    private val repositoryMock = mockk<EventListRepository>(relaxed = true)
    private val useCase = FetchEventListUseCase(repositoryMock)

    @Test
    fun `invoke Should get from repository a Flow with a list of events`() {
        val expectedEvent = createEvent()
        val expectedEventList = listOf(expectedEvent)
        val expectedSimplifiedEventList = listOf(expectedEvent.simplify())
        every { repositoryMock.eventList() } returns flowOf(expectedEventList)

        val result = useCase.invoke()

        runBlocking {
            result.test {
                assertEquals(expectedSimplifiedEventList, awaitItem())
                awaitComplete()
            }
        }
    }

    @Test
    fun `invoke Should get from repository Flow throws a Throwable`() {
        val expectedThrowable = Throwable()
        every { repositoryMock.eventList() } returns flow { throw expectedThrowable }

        val result = useCase.invoke()

        runBlocking {
            result.test {
                assertEquals(expectedThrowable, awaitError())
            }
        }
    }

}