package com.itami.calorie_tracker.core.presentation.utils

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.itami.calorie_tracker.R
import com.itami.calorie_tracker.core.presentation.navigation.Graph

sealed class BottomNavItem(
    @StringRes val titleResId: Int,
    @DrawableRes val iconResId: Int,
    val route: String,
) {

    data object Diary: BottomNavItem(
        titleResId = R.string.title_diary,
        iconResId = R.drawable.icon_pie_chart,
        route = Graph.DIARY.route
    )

    data object Recipes: BottomNavItem(
        titleResId = R.string.title_recipes,
        iconResId = R.drawable.icon_menu_book,
        route = Graph.RECIPES.route
    )

    data object Reports: BottomNavItem(
        titleResId = R.string.title_reports,
        iconResId = R.drawable.icon_progress,
        route = Graph.REPORTS.route
    )

}
