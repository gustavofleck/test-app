package com.gustavofleck.data.mappers

import com.gustavofleck.domain.models.Event
import com.gustavofleck.utils.stubs.createEventResponse
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.text.SimpleDateFormat

internal class EventMappersTest{

    private val date = "12/10/2022"
    private val dateFormatMock = mockk<SimpleDateFormat>(relaxed = true)
    private val eventMapper = EventMapper(dateFormatMock)
    private val mapper = EventListMapper(eventMapper)

    @Test
    fun `map Should return mapped event list When receive an event response list`() {
        val eventResponse = createEventResponse()
        val eventResponseList = listOf(eventResponse)
        val expectedResult = createExpectedResult()
        every { dateFormatMock.format(eventResponse.date) } returns date

        val result = mapper.map(eventResponseList)

        assertEquals(result.first(), expectedResult)
    }

    private fun createExpectedResult() = Event(
        people = listOf(),
        date = date,
        description = "Test description response",
        image = "https://imageurl.teste.response",
        longitude = -65.132132132,
        latitude = -25.465486,
        price = 32.99f,
        title = "Test Title Response",
        id = "121"
    )
}