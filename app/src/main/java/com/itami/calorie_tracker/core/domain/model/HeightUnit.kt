package com.itami.calorie_tracker.core.domain.model

import kotlin.math.roundToInt

enum class HeightUnit {
    FEET,
    METER;

    fun feetToMeters(heightValueFeet: HeightValueFeet): HeightValueMeters {
        val totalInches = heightValueFeet.feet * 12 + heightValueFeet.inches
        val meters = totalInches * 0.0254
        val centimeters = (meters * 100).roundToInt()
        val remainingCentimeters = centimeters % 100
        val metersInt = centimeters / 100
        return HeightValueMeters(meters = metersInt, centimeters = remainingCentimeters)
    }

    fun metersToFeet(heightValueMeters: HeightValueMeters): HeightValueFeet {
        val totalCentimeters = heightValueMeters.meters * 100 + heightValueMeters.centimeters
        val inches = totalCentimeters * 0.393701
        val feet = inches / 12
        val remainingInches = inches % 12
        return HeightValueFeet(feet = feet.roundToInt(), inches = remainingInches.roundToInt())
    }

    fun metersToCentimeters(heightValueMeters: HeightValueMeters): Int {
        return heightValueMeters.meters * 100 + heightValueMeters.centimeters
    }

    fun feetToCentimeters(heightValueFeet: HeightValueFeet): Int {
        val totalInches = heightValueFeet.feet * 12 + heightValueFeet.inches
        return (totalInches * 2.54).roundToInt()
    }

    fun centimetersToFeet(centimeters: Int): HeightValueFeet {
        val totalInches = (centimeters / 2.54).roundToInt()
        val feet = (totalInches / 12)
        val remainingInches = (totalInches % 12)
        return HeightValueFeet(feet = feet, inches = remainingInches)
    }

    fun centimetersToMeters(centimeters: Int): HeightValueMeters {
        val meters = centimeters / 100
        val remainingCentimeters = centimeters % 100
        return HeightValueMeters(meters = meters, centimeters = remainingCentimeters)
    }

    fun format(heightCm: Int): String {
        when(this) {
            FEET -> {
                val totalInches = (heightCm / 2.54).roundToInt()
                val feet = (totalInches / 12)
                val remainingInches = (totalInches % 12)
                return "$feet ft. $remainingInches in."
            }
            METER -> {
                return "$heightCm cm"
            }
        }
    }
}

data class HeightValueMeters(val meters: Int, val centimeters: Int)
data class HeightValueFeet(val feet: Int, val inches: Int)