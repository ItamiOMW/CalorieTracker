package com.itami.calorie_tracker.authentication_feature.domain.model

import com.itami.calorie_tracker.core.domain.exceptions.AppException
import com.itami.calorie_tracker.core.utils.AppResponse

data class RegisterResult(
    val emailException: AppException? = null,
    val passwordException: AppException? = null,
    val nameException: AppException? = null,
    val response: AppResponse<Unit>? = null,
)