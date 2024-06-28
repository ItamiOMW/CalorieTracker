package com.itami.calorie_tracker.core.domain.model

import com.itami.calorie_tracker.core.domain.exceptions.AppException
import com.itami.calorie_tracker.core.utils.AppResponse

data class ChangePasswordResult(
    val oldPasswordException: AppException? = null,
    val newPasswordException: AppException? = null,
    val repeatPasswordException: AppException? = null,
    val response: AppResponse<Unit>? = null,
)