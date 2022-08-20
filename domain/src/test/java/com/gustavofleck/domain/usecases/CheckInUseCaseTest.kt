package com.gustavofleck.domain.usecases

import app.cash.turbine.test
import com.gustavofleck.domain.exceptions.InvalidDataException
import com.gustavofleck.domain.models.CheckInResult
import com.gustavofleck.domain.repository.EventCheckInRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

internal class CheckInUseCaseTest {

    private val eventId = "1"
    private val name = "Teste"
    private val email = "teste@teste.com"
    private val repositoryMock = mockk<EventCheckInRepository>(relaxed = true)
    private val useCase = CheckInUseCase(repositoryMock)

    @Test
    fun `invoke Should get from repository a Flow with checkIn result`() {
        val expectedResponse = CheckInResult("200")
        every { repositoryMock.checkIn(eventId, name, email) } returns flowOf(expectedResponse)

        val result = useCase.invoke(eventId, name, email)

        runBlocking {
            result.test {
                Assertions.assertEquals(expectedResponse, awaitItem())
                awaitComplete()
            }
        }
    }

    @Test
    fun `invoke Should get from repository Flow throws a Throwable`() {
        val expectedThrowable = Throwable()
        every { repositoryMock.checkIn(eventId, name, email) } returns flow { throw expectedThrowable }

        val result = useCase.invoke(eventId, name, email)

        runBlocking {
            result.test {
                Assertions.assertEquals(expectedThrowable, awaitError())
            }
        }
    }

    @Test
    fun `invoke Should throws a InvalidDataException When input text is invalid`() {
        runBlocking {
            assertThrows(InvalidDataException::class.java) { useCase.invoke(eventId, "", email) }
        }
    }
}