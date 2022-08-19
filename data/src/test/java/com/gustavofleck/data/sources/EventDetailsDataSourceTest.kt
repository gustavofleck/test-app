package com.gustavofleck.data.sources

import com.gustavofleck.data.api.EventsService
import com.gustavofleck.data.mappers.EventMapper
import com.gustavofleck.utils.stubs.createEvent
import com.gustavofleck.utils.stubs.createEventResponse
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.text.SimpleDateFormat

internal class EventDetailsDataSourceTest {

    private val eventId = "121"
    private val serviceMock = mockk<EventsService>()
    private val dateFormatMock = mockk<SimpleDateFormat>(relaxed = true)
    private val mapper = EventMapper(dateFormatMock)
    private val dataSource = EventDetailsDataSource(serviceMock, mapper)

    @Test
    fun `event Should return a mapped Event When service return an EventResponse`() {
        val expectedResponse = listOf(createEventResponse())
        val expectedEvent = createEvent()
        every { dateFormatMock.format(expectedResponse.first().date) } returns expectedEvent.date
        coEvery { serviceMock.event(eventId) } returns expectedResponse

        runBlocking {
            val result = dataSource.eventDetails(eventId)

            coVerify { serviceMock.event(eventId) }
            assertEquals(result, expectedEvent)
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