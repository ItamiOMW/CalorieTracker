package com.itami.calorie_tracker.authentication_feature.presentation.screens.recommended_nutrients

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.itami.calorie_tracker.BuildConfig
import com.itami.calorie_tracker.R
import com.itami.calorie_tracker.authentication_feature.presentation.utils.OneTapSignInWithGoogle
import com.itami.calorie_tracker.core.domain.model.DailyNutrientsGoal
import com.itami.calorie_tracker.core.presentation.theme.CalorieTrackerTheme
import kotlinx.coroutines.flow.Flow

@Composable
fun RecommendedNutrientsScreen(
    onNavigateToDiary: () -> Unit,
    onNavigateToRegisterEmail: () -> Unit,
    onShowSnackbar: (message: String) -> Unit,
    state: RecommendedNutrientsState,
    uiEvent: Flow<RecommendedNutrientUiEvent>,
    onEvent: (RecommendedNutrientEvent) -> Unit,
) {
    LaunchedEffect(key1 = true) {
        uiEvent.collect { event ->
            when (event) {
                is RecommendedNutrientUiEvent.ShowSnackbar -> {
                    onShowSnackbar(event.message)
                }

                is RecommendedNutrientUiEvent.SignUpSuccessful -> {
                    onNavigateToDiary()
                }
            }
        }
    }

    OneTapSignInWithGoogle(
        opened = state.showGoogleOneTap,
        clientId = BuildConfig.GOOGLE_CLIENT_ID,
        onIdTokenReceived = { idToken ->
            onEvent(RecommendedNutrientEvent.ShowGoogleOneTap(false))
            onEvent(RecommendedNutrientEvent.SignUpWithGoogle(idToken))
        },
        onDialogDismissed = { errorMessage ->
            onEvent(RecommendedNutrientEvent.ShowGoogleOneTap(false))
            errorMessage?.let(onShowSnackbar)
        }
    )

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
            if (state.dailyNutrientsGoal != null) {
                TopSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopCenter),
                    dailyNutrientsGoal = { state.dailyNutrientsGoal }
                )
                BottomSection(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .padding(horizontal = CalorieTrackerTheme.padding.medium)
                        .padding(bottom = CalorieTrackerTheme.padding.large),
                    isLoading = state.isLoading,
                    onContinueWithGoogle = {
                        onEvent(RecommendedNutrientEvent.ShowGoogleOneTap(show = true))
                    },
                    onContinueWithEmail = {
                        onNavigateToRegisterEmail()
                    }
                )
            }
            if (state.isLoading) {
                CircularProgressIndicator(color = CalorieTrackerTheme.colors.primary)
            }
        }
    }
}

@Composable
private fun BottomSection(
    modifier: Modifier,
    isLoading: Boolean,
    onContinueWithGoogle: () -> Unit,
    onContinueWithEmail: () -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedButton(
            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Unspecified),
            border = BorderStroke(1.dp, CalorieTrackerTheme.colors.outline),
            shape = CalorieTrackerTheme.shapes.small,
            enabled = !isLoading,
            contentPadding = PaddingValues(vertical = CalorieTrackerTheme.padding.default),
            modifier = Modifier.fillMaxWidth(),
            onClick = onContinueWithGoogle
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(CalorieTrackerTheme.spacing.extraSmall)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_google),
                    contentDescription = stringResource(R.string.desc_google_icon),
                    modifier = Modifier.size(20.dp),
                )
                Text(
                    text = stringResource(R.string.continue_with_google),
                    color = CalorieTrackerTheme.colors.onBackground,
                    style = CalorieTrackerTheme.typography.bodyMedium,
                )
            }
        }
        Spacer(modifier = Modifier.height(CalorieTrackerTheme.spacing.default))
        OutlinedButton(
            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Unspecified),
            border = BorderStroke(1.dp, CalorieTrackerTheme.colors.outline),
            shape = CalorieTrackerTheme.shapes.small,
            enabled = !isLoading,
            contentPadding = PaddingValues(vertical = CalorieTrackerTheme.padding.default),
            modifier = Modifier.fillMaxWidth(),
            onClick = onContinueWithEmail
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(CalorieTrackerTheme.spacing.extraSmall)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_mail),
                    contentDescription = stringResource(R.string.desc_icon_mail),
                    tint = CalorieTrackerTheme.colors.onBackground,
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = stringResource(R.string.continue_with_email),
                    color = CalorieTrackerTheme.colors.onBackground,
                    style = CalorieTrackerTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
private fun TopSection(
    modifier: Modifier,
    dailyNutrientsGoal: () -> DailyNutrientsGoal,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = CalorieTrackerTheme.padding.large,
                    start = CalorieTrackerTheme.padding.large,
                    end = CalorieTrackerTheme.padding.large
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(R.string.recommended_pfc_screen_title),
                color = CalorieTrackerTheme.colors.onBackground,
                style = CalorieTrackerTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(CalorieTrackerTheme.spacing.small))
            Text(
                text = stringResource(R.string.recommended_pfc_screen_description),
                color = CalorieTrackerTheme.colors.onBackgroundVariant,
                style = CalorieTrackerTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.height(CalorieTrackerTheme.spacing.large))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = CalorieTrackerTheme.padding.default)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(CalorieTrackerTheme.spacing.small),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                NutrientItem(
                    modifier = Modifier.weight(1f),
                    text = stringResource(
                        R.string.proteins_to_grams,
                        dailyNutrientsGoal().proteinsGoal
                    )
                )
                NutrientItem(
                    modifier = Modifier.weight(1f),
                    text = stringResource(R.string.fats_to_grams, dailyNutrientsGoal().fatsGoal)
                )
                NutrientItem(
                    modifier = Modifier.weight(1f),
                    text = stringResource(R.string.carbs_to_grams, dailyNutrientsGoal().carbsGoal)
                )
            }
            Spacer(modifier = Modifier.height(CalorieTrackerTheme.spacing.small))
            NutrientItem(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                paddingValues = PaddingValues(
                    vertical = CalorieTrackerTheme.padding.small,
                    horizontal = CalorieTrackerTheme.padding.extraSmall
                ),
                text = stringResource(R.string.calories_amount, dailyNutrientsGoal().caloriesGoal)
            )
        }
    }
}

@Composable
private fun NutrientItem(
    modifier: Modifier,
    paddingValues: PaddingValues = PaddingValues(vertical = CalorieTrackerTheme.padding.small),
    text: String,
) {
    Surface(
        modifier = modifier,
        shape = CalorieTrackerTheme.shapes.small,
        color = CalorieTrackerTheme.colors.primary,
        contentColor = CalorieTrackerTheme.colors.onPrimary,
    ) {
        Text(
            text = text,
            style = CalorieTrackerTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
            maxLines = 1,
            modifier = Modifier.padding(paddingValues)
        )
    }
}
