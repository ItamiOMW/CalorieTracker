package com.itami.calorie_tracker.core.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val errorMessage: String? = null
)
