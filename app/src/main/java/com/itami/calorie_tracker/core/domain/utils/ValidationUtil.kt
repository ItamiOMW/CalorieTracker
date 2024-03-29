package com.itami.calorie_tracker.core.domain.utils

import com.itami.calorie_tracker.core.domain.exceptions.UserInfoException
import com.itami.calorie_tracker.core.utils.Constants

object ValidationUtil {

    fun validateHeight(heightCm: Int): UserInfoException? {
        if (heightCm > Constants.MAX_HEIGHT_CM || heightCm < Constants.MIN_HEIGHT_CM) {
            return UserInfoException.InvalidHeightException
        }
        return null
    }

    fun validateWeight(weightGrams: Int): UserInfoException? {
        if (weightGrams > Constants.MAX_WEIGHT_GRAMS || weightGrams < Constants.MIN_WEIGHT_GRAMS) {
            return UserInfoException.InvalidWeightException
        }
        return null
    }

    fun validateAge(age: Int): UserInfoException? {
        if (age > Constants.MAX_AGE || age < Constants.MIN_AGE) {
            return UserInfoException.InvalidAgeException
        }
        return null
    }

}