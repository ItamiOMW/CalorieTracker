package com.itami.calorie_tracker.core.presentation.utils

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.itami.calorie_tracker.R
import com.itami.calorie_tracker.diary_feature.presentation.DiaryGraphScreens
import com.itami.calorie_tracker.recipes_feature.presentation.RecipesGraphScreen
import com.itami.calorie_tracker.reports_feature.presentation.ReportsGraphScreen

sealed class BottomNavItem(
    @StringRes val titleResId: Int,
    @DrawableRes val iconResId: Int,
    val screenRoute: String,
) {

    data object Diary: BottomNavItem(
        titleResId = R.string.diary,
        iconResId = R.drawable.icon_pie_chart,
        screenRoute = DiaryGraphScreens.Diary.fullRoute,
    )

    data object Recipes: BottomNavItem(
        titleResId = R.string.recipes,
        iconResId = R.drawable.icon_menu_book,
        screenRoute = RecipesGraphScreen.Recipes.fullRoute,
    )

    data object Reports: BottomNavItem(
        titleResId = R.string.reports,
        iconResId = R.drawable.icon_progress,
        screenRoute = ReportsGraphScreen.Reports.fullRoute,
    )

}
