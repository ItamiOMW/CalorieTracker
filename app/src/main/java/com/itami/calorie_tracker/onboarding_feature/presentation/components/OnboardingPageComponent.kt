package com.itami.calorie_tracker.onboarding_feature.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.itami.calorie_tracker.core.presentation.theme.CalorieTrackerTheme

@Composable
fun OnboardingPageComponent(
    onboardingPage: OnboardingPage,
    isDarkTheme: Boolean,
    modifier: Modifier = Modifier,
) {
    var columnSize by remember { mutableStateOf(IntSize.Zero) }

    Column(
        modifier = modifier.onGloballyPositioned { columnSize = it.size },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = if (isDarkTheme) onboardingPage.darkImageId else onboardingPage.lightImageId,
            contentDescription = stringResource(id = onboardingPage.titleId),
            modifier = Modifier
                .height(230.dp)
        )
        Spacer(modifier = Modifier.height(CalorieTrackerTheme.spacing.medium))
        Column {
            Text(
                text = stringResource(id = onboardingPage.titleId),
                style = CalorieTrackerTheme.typography.titleLarge,
                color = CalorieTrackerTheme.colors.onBackground,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(CalorieTrackerTheme.spacing.small))
            Text(
                text = stringResource(id = onboardingPage.descId),
                style = CalorieTrackerTheme.typography.bodyLarge,
                color = CalorieTrackerTheme.colors.onBackground,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }

}


@Preview
@Composable
fun OnboardingPageComponentPreview() {
    CalorieTrackerTheme {
        OnboardingPageComponent(
            onboardingPage = OnboardingPage.Welcoming,
            isDarkTheme = false,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = CalorieTrackerTheme.padding.extraLarge,
                    end = CalorieTrackerTheme.padding.extraLarge
                )
                .wrapContentHeight()
        )
    }
}