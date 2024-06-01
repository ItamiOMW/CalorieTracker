package com.itami.calorie_tracker.authentication_feature.data.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class ResetPasswordRequest(
    val code: Int,
    val email: String,
    val newPassword: String,
)
