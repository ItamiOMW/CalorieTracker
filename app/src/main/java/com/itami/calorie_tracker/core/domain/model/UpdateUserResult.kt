package com.itami.calorie_tracker.core.domain.model

import com.itami.calorie_tracker.core.domain.exceptions.AppException
import com.itami.calorie_tracker.core.utils.AppResponse

data class UpdateUserResult(
    val ageException: AppException? = null,
    val weightException: AppException? = null,
    val heightException: AppException? = null,
    val response: AppResponse<Unit>? = null
)
