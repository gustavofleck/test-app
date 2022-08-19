package com.gustavofleck.data.api

import com.gustavofleck.data.models.CheckInRequest
import com.gustavofleck.data.models.EventResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface EventsService {

    @GET("events")
    suspend fun events(): List<EventResponse>

    @GET("events")
    suspend fun event(@Query("id") id: String): List<EventResponse>

    @POST("checkin")
    suspend fun checkIn(@Body checkInRequest: CheckInRequest)
}