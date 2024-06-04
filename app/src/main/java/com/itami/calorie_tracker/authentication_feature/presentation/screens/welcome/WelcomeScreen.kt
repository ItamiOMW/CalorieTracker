package com.itami.calorie_tracker.authentication_feature.presentation.screens.welcome

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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.itami.calorie_tracker.BuildConfig
import com.itami.calorie_tracker.R
import com.itami.calorie_tracker.authentication_feature.presentation.utils.OneTapSignInWithGoogle
import com.itami.calorie_tracker.core.domain.model.Theme
import com.itami.calorie_tracker.core.presentation.components.ObserveAsEvents
import com.itami.calorie_tracker.core.presentation.theme.CalorieTrackerTheme

@Composable
fun WelcomeScreen(
    onStart: () -> Unit,
    onGoogleLoginSuccessful: () -> Unit,
    onNavigateToLoginEmail: () -> Unit,
    onShowSnackbar: (message: String) -> Unit,
    viewModel: WelcomeViewModel = hiltViewModel(),
) {
    ObserveAsEvents(viewModel.uiEvent) { event ->
        when (event) {
            is WelcomeUiEvent.Start -> onStart()
            is WelcomeUiEvent.GoogleLoginSuccessful -> onGoogleLoginSuccessful()
            is WelcomeUiEvent.NavigateToLoginEmail -> onNavigateToLoginEmail()
            is WelcomeUiEvent.ShowSnackbar -> onShowSnackbar(event.message)
        }
    }

    WelcomeScreenContent(
        state = viewModel.state,
        onAction = viewModel::onAction
    )
}

@Preview
@Composable
fun WelcomeScreenContentPreview() {
    CalorieTrackerTheme(theme = Theme.SYSTEM_THEME) {
        WelcomeScreenContent(
            state = WelcomeState(),
            onAction = {}
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun WelcomeScreenContent(
    state: WelcomeState,
    onAction: (action: WelcomeAction) -> Unit,
) {
    OneTapSignInWithGoogle(
        opened = state.showGoogleOneTap,
        clientId = BuildConfig.GOOGLE_CLIENT_ID,
        onIdTokenReceived = { idToken ->
            onAction(WelcomeAction.GoogleIdTokenReceived(idToken))
        },
        onDialogDismissed = { message ->
            onAction(WelcomeAction.GoogleIdTokenNoteReceived(message))
        }
    )

    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showBottomSheet by remember { mutableStateOf(false) }

    if (showBottomSheet) {
        ModalBottomSheet(
            containerColor = CalorieTrackerTheme.colors.surfacePrimary,
            sheetState = bottomSheetState,
            onDismissRequest = {
                showBottomSheet = false
            }
        ) {
            BottomSheetContent(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = CalorieTrackerTheme.padding.extraLarge),
                isLoading = state.isLoading,
                onSignInWithEmailButtonClick = {
                    showBottomSheet = false
                    onAction(WelcomeAction.SignInWithEmailClick)
                },
                onSignInWithGoogleButtonClick = {
                    showBottomSheet = false
                    onAction(WelcomeAction.SignInWithGoogleClick)
                }
            )
        }
    }

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
            LogoSection(modifier = Modifier.fillMaxWidth())
            BottomSection(
                isLoading = { state.isLoading },
                onStartClick = {
                    onAction(WelcomeAction.StartClick)
                },
                onSignInClick = { showBottomSheet = true },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(horizontal = CalorieTrackerTheme.padding.medium)
                    .padding(bottom = CalorieTrackerTheme.padding.large)
            )
            if (state.isLoading) {
                CircularProgressIndicator(color = CalorieTrackerTheme.colors.primary)
            }
        }
    }
}

@Composable
private fun LogoSection(
    modifier: Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = R.drawable.app_logo),
            contentDescription = stringResource(R.string.desc_app_logo),
            tint = CalorieTrackerTheme.colors.primary,
            modifier = Modifier.size(100.dp)
        )
        Spacer(modifier = Modifier.height(CalorieTrackerTheme.spacing.default))
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.welcome),
                style = CalorieTrackerTheme.typography.titleLarge,
                color = CalorieTrackerTheme.colors.onBackground,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(CalorieTrackerTheme.spacing.tiny))
            Text(
                text = stringResource(R.string.start_or_sign_in),
                style = CalorieTrackerTheme.typography.bodyLarge,
                color = CalorieTrackerTheme.colors.onBackgroundVariant,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun BottomSection(
    isLoading: () -> Boolean,
    onStartClick: () -> Unit,
    onSignInClick: () -> Unit,
    modifier: Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = CalorieTrackerTheme.colors.primary,
                contentColor = CalorieTrackerTheme.colors.onPrimary,
            ),
            contentPadding = PaddingValues(
                vertical = CalorieTrackerTheme.padding.default
            ),
            shape = CalorieTrackerTheme.shapes.small,
            enabled = !isLoading(),
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                onStartClick()
            },
        ) {
            Text(
                text = stringResource(R.string.start),
                style = CalorieTrackerTheme.typography.titleSmall,
            )
        }
        Spacer(modifier = Modifier.height(CalorieTrackerTheme.spacing.medium))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(CalorieTrackerTheme.spacing.none)
        ) {
            Text(
                text = stringResource(R.string.already_have_an_account_qm),
                color = CalorieTrackerTheme.colors.onBackground,
                style = CalorieTrackerTheme.typography.bodyMedium,
            )
            TextButton(
                onClick = {
                    onSignInClick()
                },
                enabled = !isLoading(),
                contentPadding = PaddingValues(CalorieTrackerTheme.padding.tiny)
            ) {
                Text(
                    text = stringResource(R.string.sign_in),
                    color = CalorieTrackerTheme.colors.primary,
                    style = CalorieTrackerTheme.typography.labelLarge,
                )
            }
        }
    }
}

@Composable
private fun BottomSheetContent(
    modifier: Modifier,
    isLoading: Boolean,
    onSignInWithGoogleButtonClick: () -> Unit,
    onSignInWithEmailButtonClick: () -> Unit,
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = CalorieTrackerTheme.padding.medium),
            onClick = {
                onSignInWithGoogleButtonClick()
            }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(CalorieTrackerTheme.spacing.extraSmall)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_google),
                    contentDescription = stringResource(R.string.desc_google_icon),
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = stringResource(R.string.sign_in_with_google),
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = CalorieTrackerTheme.padding.medium),
            onClick = {
                onSignInWithEmailButtonClick()
            }
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
                    text = stringResource(R.string.sign_in_with_email),
                    color = CalorieTrackerTheme.colors.onBackground,
                    style = CalorieTrackerTheme.typography.bodyMedium
                )
            }
        }
    }
}