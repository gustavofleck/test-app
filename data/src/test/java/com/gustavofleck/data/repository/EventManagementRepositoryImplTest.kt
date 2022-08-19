package com.gustavofleck.data.repository

import app.cash.turbine.test
import com.gustavofleck.data.errors.handler.EventsErrorHandler
import com.gustavofleck.data.sources.EventDetailsDataSource
import com.gustavofleck.utils.stubs.createEvent
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class EventManagementRepositoryImplTest {

    private val eventId = "121"
    private val dataSourceMock = mockk<EventDetailsDataSource>(relaxed = true)
    private val errorHandlerMock = mockk<EventsErrorHandler>(relaxed = true)
    private val repository = EventManagementRepositoryImpl(dataSourceMock, errorHandlerMock)

    @Test
    fun `eventDetails Should return an Event When data source returns an Event`() {
        val expectedData = createEvent()
        coEvery { dataSourceMock.eventDetails(eventId) } returns expectedData

        val result = repository.eventDetails(eventId)

        runBlocking {
            result.test {
                Assertions.assertEquals(expectedData, awaitItem())
                awaitComplete()
            }
        }
    }

    @Test
    fun `eventList Should throws a Throwable When data source throws an Exception`() {
        val expectedThrowable = Throwable()
        coEvery { dataSourceMock.eventDetails(eventId) } throws expectedThrowable
        coEvery { errorHandlerMock.handle(expectedThrowable) } returns expectedThrowable

        val result = repository.eventDetails(eventId)

        runBlocking {
            result.test {
                Assertions.assertEquals(expectedThrowable, awaitError())
            }
        }
    }
}