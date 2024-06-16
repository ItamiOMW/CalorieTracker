package com.itami.calorie_tracker.core.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.itami.calorie_tracker.core.domain.model.Theme
import com.itami.calorie_tracker.core.presentation.theme.CalorieTrackerTheme

@Composable
fun UnderlinedFieldWithValue(
    modifier: Modifier = Modifier,
    fieldName: String,
    fieldValue: String,
    nameTextStyle: TextStyle = CalorieTrackerTheme.typography.bodyMedium,
    valueTextStyle: TextStyle = CalorieTrackerTheme.typography.labelLarge,
    nameTextColor: Color = CalorieTrackerTheme.colors.onBackground,
    valueTextColor: Color = CalorieTrackerTheme.colors.primary,
    dividerColor: Color = CalorieTrackerTheme.colors.outline,
    onClick: () -> Unit = {},
) {
    Column(
        modifier = modifier.clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier
                .padding(CalorieTrackerTheme.padding.default)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = fieldName,
                style = nameTextStyle,
                color = nameTextColor
            )
            Text(
                text = fieldValue,
                style = valueTextStyle,
                color = valueTextColor
            )
        }
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = dividerColor
        )
    }
}

@Preview
@Composable
fun UnderlinedFieldWithValuePreview() {
    CalorieTrackerTheme(theme = Theme.SYSTEM_THEME) {
        UnderlinedFieldWithValue(
            fieldName = "Goal",
            fieldValue = "Gain Weight",
            onClick = {}
        )
    }
}