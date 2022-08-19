package com.gustavofleck.data.repository

import app.cash.turbine.test
import com.gustavofleck.data.errors.handler.EventsErrorHandler
import com.gustavofleck.data.sources.EventListDataSource
import com.gustavofleck.utils.stubs.createEvent
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class EventListRepositoryImplTest {

    private val dataSourceMock = mockk<EventListDataSource>(relaxed = true)
    private val errorHandlerMock = mockk<EventsErrorHandler>(relaxed = true)
    private val repository = EventListRepositoryImpl(dataSourceMock, errorHandlerMock)

    @Test
    fun `eventList Should return a flow of list fo Events When data source returns a list of Events`() {
        val expectedData = listOf(createEvent(), createEvent())
        coEvery { dataSourceMock.eventList() } returns expectedData

        val result = repository.eventList()

        runBlocking {
            result.test {
                assertEquals(expectedData, awaitItem())
                awaitComplete()
            }
        }
    }

    @Test
    fun `eventList Should throws a Throwable When data source throws an Exception`() {
        val expectedThrowable = Throwable()
        coEvery { dataSourceMock.eventList() } throws expectedThrowable
        coEvery { errorHandlerMock.handle(expectedThrowable) } returns expectedThrowable

        val result = repository.eventList()

        runBlocking {
            result.test {
                assertEquals(expectedThrowable, awaitError())
            }
        }
    }
}