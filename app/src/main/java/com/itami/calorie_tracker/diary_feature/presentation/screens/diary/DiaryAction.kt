package com.itami.calorie_tracker.diary_feature.presentation.screens.diary

import java.time.ZonedDateTime

sealed class DiaryAction {

    data class ShowDatePicker(val show: Boolean) : DiaryAction()

    data class ChangeDate(val date: ZonedDateTime) : DiaryAction()

    data object AddConsumedWaterClick : DiaryAction()

    data object RemoveConsumedWaterClick : DiaryAction()

    data object Refresh : DiaryAction()

    data class MealClick(val mealId: Int) : DiaryAction()

    data object NewMealClick : DiaryAction()

    data object ProfilePictureClick : DiaryAction()

}
