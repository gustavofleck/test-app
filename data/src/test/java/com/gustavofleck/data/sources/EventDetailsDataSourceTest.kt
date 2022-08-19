package com.gustavofleck.data.sources

import com.gustavofleck.data.api.EventsService
import com.gustavofleck.data.mappers.EventListMapper
import com.gustavofleck.data.mappers.EventMapper
import com.gustavofleck.utils.stubs.createEventResponse
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class EventDetailsDataSourceTest {

    private val eventId = "121"
    private val serviceMock = mockk<EventsService>()
    private val mapper = EventMapper()
    private val dataSource = EventDetailsDataSource(serviceMock, mapper)

    @Test
    fun `event Should return a mapped Event When service return an EventResponse`() {
        val expectedResponse = createEventResponse()
        coEvery { serviceMock.event(eventId) } returns expectedResponse

        runBlocking { dataSource.eventDetails(eventId) }

        coVerify {
            serviceMock.event(eventId)
            mapper.map(expectedResponse)
        }
    }

    @Test
    fun `eventList Should throws an exception When service throws an exception`() {
        val expectedThrowable = Throwable()
        coEvery { serviceMock.event(eventId) } throws expectedThrowable

        runBlocking {
            assertThrows<Throwable> { dataSource.eventDetails(eventId) }
        }
    }

}