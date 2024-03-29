package com.itami.calorie_tracker.core.utils

import com.itami.calorie_tracker.core.domain.exceptions.AppException

sealed class AppResponse<out T> {

    data class Success<T>(val data: T) : AppResponse<T>()

    data class Failed<T>(val appException: AppException, val message: String?) : AppResponse<T>()

    companion object {

        fun <T> success(data: T) = Success(data)

        fun <T> failed(appException: AppException, message: String? = null) = Failed<T>(appException, message)

    }
}
