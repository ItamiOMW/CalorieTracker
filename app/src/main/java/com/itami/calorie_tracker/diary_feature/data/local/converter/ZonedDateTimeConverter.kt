package com.itami.calorie_tracker.diary_feature.data.local.converter

import androidx.room.TypeConverter
import java.time.ZoneId
import java.time.ZonedDateTime

class ZonedDateTimeConverter {

    @TypeConverter
    fun fromZonedDateTime(zonedDateTime: ZonedDateTime?): String? {
        return zonedDateTime?.withZoneSameInstant(ZoneId.of("UTC"))?.toString()
    }

    @TypeConverter
    fun toZonedDateTime(value: String?): ZonedDateTime? {
        return value?.let { ZonedDateTime.parse(it) }
    }
}