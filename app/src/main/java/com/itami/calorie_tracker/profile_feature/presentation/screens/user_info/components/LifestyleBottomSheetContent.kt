package com.itami.calorie_tracker.profile_feature.presentation.screens.user_info.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.itami.calorie_tracker.R
import com.itami.calorie_tracker.core.domain.model.Lifestyle
import com.itami.calorie_tracker.core.presentation.theme.CalorieTrackerTheme

@Composable
fun LifestyleBottomSheetContent(
    modifier: Modifier = Modifier,
    initialLifestyle: Lifestyle = Lifestyle.LOW_ACTIVE,
    onConfirm: (lifestyle: Lifestyle) -> Unit,
) {
    var lifestyleState by remember { mutableStateOf(initialLifestyle) }

    val lifestyles = remember {
        listOf(
            Lifestyle.SEDENTARY,
            Lifestyle.LOW_ACTIVE,
            Lifestyle.ACTIVE,
            Lifestyle.VERY_ACTIVE,
        )
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(id = R.string.lifestyle),
            style = CalorieTrackerTheme.typography.titleMedium,
            color = CalorieTrackerTheme.colors.onSurfacePrimary,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(CalorieTrackerTheme.spacing.large))
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(CalorieTrackerTheme.spacing.tiny)
        ) {
            lifestyles.forEach { lifestyle ->
                LifestyleItem(
                    selected = lifestyle == lifestyleState,
                    lifestyle = lifestyle,
                    onClick = { lifestyleState = lifestyle },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = CalorieTrackerTheme.padding.default)
                )
            }
        }
        Spacer(modifier = Modifier.height(CalorieTrackerTheme.spacing.large))
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = CalorieTrackerTheme.colors.primary,
                contentColor = CalorieTrackerTheme.colors.onPrimary,
            ),
            contentPadding = PaddingValues(vertical = CalorieTrackerTheme.padding.default),
            shape = CalorieTrackerTheme.shapes.small,
            modifier = Modifier.fillMaxWidth(),
            onClick = { onConfirm(lifestyleState) }
        ) {
            Text(
                text = stringResource(id = R.string.done),
                style = CalorieTrackerTheme.typography.labelLarge,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun LifestyleItem(
    selected: Boolean,
    lifestyle: Lifestyle,
    onClick: () -> Unit,
    modifier: Modifier,
) {
    Card(
        modifier = modifier,
        shape = CalorieTrackerTheme.shapes.small,
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent,
            contentColor = CalorieTrackerTheme.colors.onBackground,
        ),
        border = if (selected) BorderStroke(1.dp, CalorieTrackerTheme.colors.primary) else null,
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        onClick = onClick,
    ) {
        Text(
            text = stringResource(
                id = when (lifestyle) {
                    Lifestyle.SEDENTARY -> {
                        R.string.lifestyle_sedentary
                    }

                    Lifestyle.LOW_ACTIVE -> {
                        R.string.lifestyle_low_active
                    }

                    Lifestyle.ACTIVE -> {
                        R.string.lifestyle_active
                    }

                    Lifestyle.VERY_ACTIVE -> {
                        R.string.lifestyle_very_active
                    }
                }
            ),
            style = CalorieTrackerTheme.typography.labelLarge,
            textAlign = TextAlign.Center,
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = CalorieTrackerTheme.padding.default),
        )
    }
}