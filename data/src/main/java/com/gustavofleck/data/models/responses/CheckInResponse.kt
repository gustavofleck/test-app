package com.gustavofleck.data.models.responses

import com.google.gson.annotations.SerializedName
import com.gustavofleck.domain.models.CheckInResult

data class CheckInResponse(
    @SerializedName("code") val code: String?
)

fun CheckInResponse.map() = CheckInResult(code.orEmpty())
