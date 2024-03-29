package com.itami.calorie_tracker.core.domain.exceptions

sealed class UserInfoException: AppException() {

    data object InvalidAgeException: UserInfoException()

    data object InvalidHeightException: UserInfoException()

    data object InvalidWeightException: UserInfoException()

}