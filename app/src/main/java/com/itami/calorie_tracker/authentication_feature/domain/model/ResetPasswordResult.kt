package com.itami.calorie_tracker.authentication_feature.domain.model

import com.itami.calorie_tracker.core.domain.exceptions.AppException
import com.itami.calorie_tracker.core.utils.AppResponse

data class ResetPasswordResult(
    val passwordException: AppException? = null,
    val repeatPasswordException: AppException? = null,
    val codeException: AppException? = null,
    val response: AppResponse<Unit>? = null,
)