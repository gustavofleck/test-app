package com.gustavofleck.data.errors.handler

import com.gustavofleck.domain.exceptions.ConnectionException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class EventsErrorHandler {
    fun handle(error: Throwable): Throwable {
        return when (error) {
            is UnknownHostException, is SocketTimeoutException -> ConnectionException()
            else -> error
        }
    }
}