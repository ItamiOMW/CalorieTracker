package com.itami.calorie_tracker.core.domain.model

import kotlin.math.roundToInt

enum class WeightUnit {
    POUND, KILOGRAM;

    fun toGrams(weight: Float): Int {
        return when(this) {
            POUND -> {
                poundsToGrams(weight)
            }
            KILOGRAM -> {
                kilogramsToGrams(weight)
            }
        }
    }

    fun gramsToPounds(grams: Int): Float {
        return grams / 453.592f
    }

    fun poundsToGrams(pounds: Float): Int {
        return (pounds * 453.592f).roundToInt()
    }

    fun poundsToKilograms(pounds: Float): Float {
        return pounds * 0.453592f
    }

    fun gramsToKilograms(grams: Int): Float {
        return grams / 1000.0f
    }

    fun kilogramsToGrams(kilograms: Float): Int {
        return (kilograms * 1000).roundToInt()
    }

    fun kilogramsToPounds(kilograms: Float): Float {
        return kilograms * 2.20462f
    }

    fun convert(fromUnit: WeightUnit, toUnit: WeightUnit, weight: Float): Float {
        if (fromUnit == toUnit) return weight
        return when(fromUnit) {
            POUND -> {
                poundsToKilograms(pounds = weight)
            }

            KILOGRAM -> {
                kilogramsToPounds(kilograms = weight)
            }
        }
    }

    fun convert(weightGrams: Int): Float {
        return when(this) {
            POUND -> {
                gramsToPounds(weightGrams)
            }

            KILOGRAM -> {
                gramsToKilograms(weightGrams)
            }
        }
    }

    fun format(weight: Float): String {
        return when(this) {
            POUND -> {
                "$weight lbs"
            }
            KILOGRAM -> {
                "$weight kg"
            }
        }
    }

    fun format(weightGrams: Int): String {
        val weight = convert(weightGrams)
        val formattedWeight = String.format("%.1f", weight)
        return when(this) {
            POUND -> {
                "$formattedWeight lbs"
            }
            KILOGRAM -> {
                "$formattedWeight kg"
            }
        }
    }
}