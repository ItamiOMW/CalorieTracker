package com.itami.calorie_tracker.profile_feature.presentation.screens.calorie_intake

sealed class CalorieIntakeAction {

    data object CaloriesFieldClick : CalorieIntakeAction()

    data object ProteinsFieldClick : CalorieIntakeAction()

    data object FatsFieldClick : CalorieIntakeAction()

    data object CarbsFieldClick : CalorieIntakeAction()

    data object WaterFieldClick : CalorieIntakeAction()

    data class ChangeCalories(val calories: Int): CalorieIntakeAction()

    data class ChangeProteins(val proteins: Int): CalorieIntakeAction()

    data class ChangeFats(val fats: Int): CalorieIntakeAction()

    data class ChangeCarbs(val carbs: Int): CalorieIntakeAction()

    data class ChangeWater(val waterMl: Int): CalorieIntakeAction()

    data object DismissBottomSheet : CalorieIntakeAction()

    data object NavigateBackClick : CalorieIntakeAction()

    data object SaveClick : CalorieIntakeAction()

}