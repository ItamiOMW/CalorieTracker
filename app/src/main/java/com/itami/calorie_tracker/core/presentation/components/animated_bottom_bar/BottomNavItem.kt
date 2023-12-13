package com.itami.calorie_tracker.core.presentation.components.animated_bottom_bar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.itami.calorie_tracker.core.presentation.theme.CalorieTrackerTheme
import com.itami.calorie_tracker.core.presentation.utils.BottomNavItem

@Composable
fun BottomNavItem(
    bottomNavItem: BottomNavItem,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    selectedColor: Color = CalorieTrackerTheme.colors.onPrimary,
    unselectedColor: Color = CalorieTrackerTheme.colors.navigationItem,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(CalorieTrackerTheme.spacing.none)
    ) {
        Icon(
            painter = painterResource(id = bottomNavItem.iconResId),
            contentDescription = stringResource(id = bottomNavItem.titleResId),
            tint = if (isSelected) selectedColor else unselectedColor,
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = stringResource(id = bottomNavItem.titleResId),
            style = CalorieTrackerTheme.typography.bodySmall,
            color = if (isSelected) selectedColor else unselectedColor,
            textAlign = TextAlign.Center,
        )
    }
}