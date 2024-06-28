package com.itami.calorie_tracker.core.utils

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime

object DateTimeUtil {

    fun stringToDateTime(
        datetimeStr: String,
        zoneId: ZoneId = ZoneId.systemDefault(),
    ): LocalDateTime {
        return ZonedDateTime.parse(datetimeStr)
            .withZoneSameInstant(zoneId)
            .toLocalDateTime()
    }

    fun stringToZonedDateTime(
        datetimeStr: String,
        zoneId: ZoneId = ZoneId.systemDefault(),
    ): ZonedDateTime {
        return ZonedDateTime.parse(datetimeStr)
            .withZoneSameInstant(zoneId)
    }

    fun changeDateTimeStrZone(
        datetimeStr: String,
        zoneId: ZoneId,
    ): String {
        return ZonedDateTime.parse(datetimeStr)
            .withZoneSameInstant(zoneId)
            .toString()
    }

    fun getCurrentZonedDateTime(
        zoneId: ZoneId = ZoneId.systemDefault(),
    ): ZonedDateTime {
        return ZonedDateTime.now(zoneId)
    }

    fun getCurrentDateTimeString(
        zoneId: ZoneId = ZoneId.systemDefault(),
    ): String {
        return ZonedDateTime.now(zoneId).toString()
    }

    fun epochMilliToDate(
        timestamp: Long,
        timestampZoneId: ZoneId = ZoneId.systemDefault(),
    ): ZonedDateTime {
        val instant = Instant.ofEpochMilli(timestamp)
        return instant.atZone(timestampZoneId)
    }
}

