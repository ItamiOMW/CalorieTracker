package com.itami.calorie_tracker.core.domain.utils

import com.itami.calorie_tracker.core.domain.exceptions.InvalidAgeException
import com.itami.calorie_tracker.core.domain.exceptions.InvalidHeightException
import com.itami.calorie_tracker.core.domain.exceptions.InvalidWeightException
import com.itami.calorie_tracker.core.utils.Constants

object ValidationUtil {

    fun validateHeight(heightCm: Int): Exception? {
        if (heightCm > Constants.MAX_HEIGHT_CM || heightCm < Constants.MIN_HEIGHT_CM) {
            return InvalidHeightException
        }
        return null
    }

    fun validateWeight(weightGrams: Int): Exception? {
        if (weightGrams > Constants.MAX_WEIGHT_GRAMS || weightGrams < Constants.MIN_WEIGHT_GRAMS) {
            return InvalidWeightException
        }
        return null
    }

    fun validateAge(age: Int): Exception? {
        if (age > Constants.MAX_AGE || age < Constants.MIN_AGE) {
            return InvalidAgeException
        }
        return null
    }

}