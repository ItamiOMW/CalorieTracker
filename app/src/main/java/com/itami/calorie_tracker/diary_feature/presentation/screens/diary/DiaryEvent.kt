package com.itami.calorie_tracker.diary_feature.presentation.screens.diary

import java.time.ZonedDateTime

sealed class DiaryEvent {

    data class ShowDatePicker(val show: Boolean): DiaryEvent()

    data class ChangeDate(val date: ZonedDateTime): DiaryEvent()

    data object AddConsumedWater: DiaryEvent()

    data object RemoveConsumedWater: DiaryEvent()

    data object Refresh: DiaryEvent()

}
