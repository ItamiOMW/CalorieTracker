package com.itami.calorie_tracker.diary_feature.domain.exceptions

import com.itami.calorie_tracker.core.domain.exceptions.AppException

sealed class MealException: AppException() {

    data object EmptyMealNameException: MealException()

    data object InvalidWaterMlException: MealException()

}