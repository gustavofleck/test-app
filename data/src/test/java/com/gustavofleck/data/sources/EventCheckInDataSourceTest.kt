package com.gustavofleck.data.sources

import com.gustavofleck.data.api.EventsService
import com.gustavofleck.data.models.requests.CheckInRequest
import com.gustavofleck.data.models.responses.CheckInResponse
import com.gustavofleck.data.models.responses.map
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class EventCheckInDataSourceTest {

    private val request = CheckInRequest("1", "Teste", "teste@teste.com")
    private val serviceMock = mockk<EventsService>()
    private val dataSource = EventCheckInDataSource(serviceMock)

    @Test
    fun `eventList Should return a mapped list of Event When service return a list of EventResponse`() {
        val expectedResponse = CheckInResponse("200")
        val expectedResult = expectedResponse.map()
        coEvery { serviceMock.checkIn(request) } returns expectedResponse

        runBlocking {
            val result = dataSource.checkIn(request)

            coVerify { serviceMock.checkIn(request) }
            assertEquals(result, expectedResult)
        }
    }

    @Test
    fun `eventList Should throws an exception When service throws an exception`() {
        val expectedThrowable = Throwable()
        coEvery { serviceMock.checkIn(request) } throws expectedThrowable

        runBlocking {
            assertThrows<Throwable> { dataSource.checkIn(request) }
        }
    }
}