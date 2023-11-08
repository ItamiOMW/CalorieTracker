package com.itami.calorie_tracker.authentication_feature.data.remote.response

import com.itami.calorie_tracker.core.data.remote.response.UserResponse
import kotlinx.serialization.Serializable

@Serializable
data class UserWithTokenResponse(
    val user: UserResponse,
    val token: String
)
