package com.itami.calorie_tracker.diary_feature.presentation.screens.diary

import java.time.ZonedDateTime

sealed class DiaryAction {

    data class ShowDatePicker(val show: Boolean): DiaryAction()

    data class ChangeDate(val date: ZonedDateTime): DiaryAction()

    data object AddConsumedWater: DiaryAction()

    data object RemoveConsumedWater: DiaryAction()

    data object Refresh: DiaryAction()

}
