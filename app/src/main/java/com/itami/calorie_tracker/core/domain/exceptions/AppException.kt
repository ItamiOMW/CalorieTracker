package com.itami.calorie_tracker.core.domain.exceptions

open class AppException(
    message: String? = null,
    cause: Exception? = null
): Exception(message, cause) {

    // General exceptions

    object GeneralException: AppException()

    object NetworkException: AppException()

    object ServerError: AppException()

    object UnauthorizedException: AppException()

    object TooManyRequestsException: AppException()

}