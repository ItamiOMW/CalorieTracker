package com.itami.calorie_tracker.authentication_feature.presentation.screens.age

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.itami.calorie_tracker.R
import com.itami.calorie_tracker.core.domain.model.Theme
import com.itami.calorie_tracker.core.presentation.components.ObserveAsEvents
import com.itami.calorie_tracker.core.presentation.theme.CalorieTrackerTheme


@Composable
fun AgeScreen(
    onAgeSaved: () -> Unit,
    onNavigateBack: () -> Unit,
    onShowSnackbar: (message: String) -> Unit,
    viewModel: AgeViewModel = hiltViewModel(),
) {
    ObserveAsEvents(viewModel.uiEvent) { event ->
        when (event) {
            is AgeUiEvent.AgeSaved -> onAgeSaved()
            is AgeUiEvent.NavigateBack -> onNavigateBack()
            is AgeUiEvent.ShowSnackbar -> onShowSnackbar(event.message)
        }
    }

    AgeScreenContent(
        state = viewModel.state,
        onAction = viewModel::onAction
    )
}

@Preview
@Composable
fun AgeScreenContentPreview() {
    CalorieTrackerTheme(theme = Theme.SYSTEM_THEME) {
        AgeScreenContent(
            state = AgeState(),
            onAction = {}
        )
    }
}

@Composable
private fun AgeScreenContent(
    state: AgeState,
    onAction: (AgeAction) -> Unit,
) {
    Scaffold(
        containerColor = CalorieTrackerTheme.colors.background,
        contentColor = CalorieTrackerTheme.colors.onBackground
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            contentAlignment = Alignment.Center
        ) {
            TopSection(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth()
                    .padding(
                        top = CalorieTrackerTheme.padding.large,
                        start = CalorieTrackerTheme.padding.large,
                        end = CalorieTrackerTheme.padding.large
                    )
            )
            AgeSection(
                age = state.age,
                onAgeValueChange = { age ->
                    onAction(AgeAction.AgeValueChange(age))
                },
                modifier = Modifier
                    .widthIn(max = 200.dp)
                    .padding(start = CalorieTrackerTheme.padding.medium)
            )
            BottomSection(
                onNavigateNextClick = {
                    onAction(AgeAction.NavigateNextClick)
                },
                onNavigateBackClick = {
                    onAction(AgeAction.NavigateBackClick)
                },
                isLoading = state.isLoading,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(
                        start = CalorieTrackerTheme.padding.medium,
                        end = CalorieTrackerTheme.padding.medium,
                        bottom = CalorieTrackerTheme.padding.large
                    )
            )
        }
    }
}

@Composable
private fun TopSection(
    modifier: Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.age_screen_title),
            color = CalorieTrackerTheme.colors.onBackground,
            style = CalorieTrackerTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(CalorieTrackerTheme.spacing.small))
        Text(
            text = stringResource(R.string.age_screen_description),
            color = CalorieTrackerTheme.colors.onBackgroundVariant,
            style = CalorieTrackerTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun AgeSection(
    age: String,
    onAgeValueChange: (age: String) -> Unit,
    modifier: Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(CalorieTrackerTheme.spacing.small)
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .align(Alignment.Top),
            value = age,
            onValueChange = { newValue ->
                val newText = newValue.takeIf { input ->
                    input.isEmpty() || input.toIntOrNull() != null
                }
                newText?.let { onAgeValueChange(it) }
            },
            textStyle = CalorieTrackerTheme.typography.bodyLarge,
            colors = TextFieldDefaults.colors(
                focusedTextColor = CalorieTrackerTheme.colors.onBackground,
                unfocusedTextColor = CalorieTrackerTheme.colors.onBackground,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                cursorColor = CalorieTrackerTheme.colors.primary,
                focusedIndicatorColor = CalorieTrackerTheme.colors.primary,
                unfocusedIndicatorColor = CalorieTrackerTheme.colors.primary,
            ),
            label = { },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            singleLine = true,
        )
        Text(
            modifier = Modifier.align(Alignment.Bottom),
            text = stringResource(R.string.age_unit),
            color = CalorieTrackerTheme.colors.primary,
            style = CalorieTrackerTheme.typography.bodyLarge,
        )
    }
}

@Composable
private fun BottomSection(
    onNavigateNextClick: () -> Unit,
    onNavigateBackClick: () -> Unit,
    isLoading: Boolean,
    modifier: Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TextButton(
            modifier = Modifier.align(Alignment.Bottom),
            onClick = {
                onNavigateBackClick()
            }
        ) {
            Text(
                text = stringResource(R.string.back),
                color = CalorieTrackerTheme.colors.onBackgroundVariant,
                style = CalorieTrackerTheme.typography.bodyLarge,
            )
        }
        FloatingActionButton(
            containerColor = CalorieTrackerTheme.colors.primary,
            contentColor = CalorieTrackerTheme.colors.onPrimary,
            onClick = {
                if (!isLoading) {
                    onNavigateNextClick()
                }
            },
            modifier = Modifier
                .size(56.dp)
                .shadow(elevation = 5.dp, shape = CircleShape)
                .clip(CircleShape),
        ) {
            Icon(
                painter = painterResource(id = R.drawable.icon_navigate_next),
                contentDescription = stringResource(R.string.desc_icon_navigate_next),
                modifier = Modifier.size(24.dp)
            )
        }
    }
}