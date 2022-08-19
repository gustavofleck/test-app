package com.gustavofleck.data.repository

import app.cash.turbine.test
import com.gustavofleck.data.errors.handler.EventsErrorHandler
import com.gustavofleck.data.models.requests.CheckInRequest
import com.gustavofleck.data.models.responses.CheckInResponse
import com.gustavofleck.data.sources.EventCheckInDataSource
import com.gustavofleck.data.sources.EventListDataSource
import com.gustavofleck.domain.models.CheckInResult
import com.gustavofleck.utils.stubs.createEvent
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class EventCheckInRepositoryImplTest {

    private val eventId = "1"
    private val name = "Teste"
    private val email = "teste@teste.com"
    private val request = CheckInRequest(eventId, name, email)
    private val dataSourceMock = mockk<EventCheckInDataSource>(relaxed = true)
    private val errorHandlerMock = mockk<EventsErrorHandler>(relaxed = true)
    private val repository = EventCheckInRepositoryImpl(dataSourceMock, errorHandlerMock)

    @Test
    fun `eventList Should return a flow of list fo Events When data source returns a list of Events`() {
        val expectedData = CheckInResult("200")
        coEvery { dataSourceMock.checkIn(request) } returns expectedData

        val result = repository.checkIn(eventId, name, email)

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
        coEvery { dataSourceMock.checkIn(request) } throws expectedThrowable
        coEvery { errorHandlerMock.handle(expectedThrowable) } returns expectedThrowable

        val result = repository.checkIn(eventId, name, email)

        runBlocking {
            result.test {
                Assertions.assertEquals(expectedThrowable, awaitError())
            }
        }
    }

}