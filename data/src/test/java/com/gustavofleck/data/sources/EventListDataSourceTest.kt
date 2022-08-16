package com.gustavofleck.data.sources

import com.gustavofleck.data.api.EventsService
import com.gustavofleck.data.mappers.EventMapper
import com.gustavofleck.utils.stubs.createEventResponse
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class EventListDataSourceTest {

    private val serviceMock = mockk<EventsService>()
    private val mapper = EventMapper()
    private val dataSource = EventListDataSource(serviceMock, mapper)

    @Test
    fun `eventList Should return a mapped list of Event When service return a list of EventResponse`() {
        val expectedResponse = listOf(createEventResponse(), createEventResponse())
        coEvery { serviceMock.events() } returns expectedResponse

        runBlocking { dataSource.eventList() }

        coVerify {
            serviceMock.events()
            mapper.map(expectedResponse)
        }
    }

    @Test
    fun `eventList Should throws an exception When service throws an exception`() {
        val expectedThrowable = Throwable()
        coEvery { serviceMock.events() } throws expectedThrowable

        runBlocking {
            assertThrows<Throwable> { dataSource.eventList() }
        }
    }
}