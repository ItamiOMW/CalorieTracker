package com.itami.calorie_tracker.core.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.itami.calorie_tracker.core.presentation.theme.CalorieTrackerTheme

@Composable
fun OptionItem(
    modifier: Modifier = Modifier,
    optionText: String,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingContent: (@Composable () -> Unit)? = null,
    containerColor: Color = Color.Transparent,
    textColor: Color = CalorieTrackerTheme.colors.onBackground,
    shape: Shape = RoundedCornerShape(0.dp),
    contentPadding: PaddingValues = PaddingValues(CalorieTrackerTheme.padding.default),
    onClick: () -> Unit,
) {
    Surface(
        modifier = modifier,
        onClick = onClick,
        shape = shape,
        color = containerColor,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(contentPadding),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(CalorieTrackerTheme.spacing.small),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (leadingIcon != null) {
                    leadingIcon()
                }
                Text(
                    text = optionText,
                    style = CalorieTrackerTheme.typography.bodyMedium,
                    color = textColor
                )
            }
            if (trailingContent != null) {
                trailingContent()
            } else {
                Spacer(modifier = Modifier)
            }
        }
    }
}