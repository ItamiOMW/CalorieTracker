package com.itami.calorie_tracker.core.utils

sealed class AppResponse<out T> {

    data class Success<T>(val data: T) : AppResponse<T>()

    data class Failed<T>(val exception: Exception, val message: String?) : AppResponse<T>()

    companion object {

        fun <T> success(data: T) = Success(data)

        fun <T> failed(exception: Exception, message: String? = null) = Failed<T>(exception, message)

    }
}
