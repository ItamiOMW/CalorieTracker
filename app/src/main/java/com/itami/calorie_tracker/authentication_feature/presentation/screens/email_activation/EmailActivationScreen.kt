package com.itami.calorie_tracker.authentication_feature.presentation.screens.email_activation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.itami.calorie_tracker.R
import com.itami.calorie_tracker.core.domain.model.Theme
import com.itami.calorie_tracker.core.presentation.components.ObserveAsEvents
import com.itami.calorie_tracker.core.presentation.theme.CalorieTrackerTheme

@Composable
fun EmailActivationScreen(
    onNavigateToLogin: () -> Unit,
    onNavigateBack: () -> Unit,
    onShowSnackbar: (message: String) -> Unit,
    viewModel: EmailActivationViewModel = hiltViewModel(),
) {
    ObserveAsEvents(viewModel.uiEvent) { event ->
        when (event) {
            is EmailActivationUiEvent.NavigateBack -> onNavigateBack()
            is EmailActivationUiEvent.NavigateToLogin -> onNavigateToLogin()
            is EmailActivationUiEvent.ShowSnackbar -> onShowSnackbar(event.message)
        }
    }

    EmailActivationScreenContent(
        state = viewModel.state,
        onAction = viewModel::onAction
    )
}

@Preview
@Composable
fun RegisterEmailScreenContentPreview() {
    CalorieTrackerTheme(theme = Theme.SYSTEM_THEME) {
        EmailActivationScreenContent(
            state = EmailActivationState(),
            onAction = {}
        )
    }
}

@Composable
private fun EmailActivationScreenContent(
    state: EmailActivationState,
    onAction: (EmailActivationAction) -> Unit,
) {
    Scaffold(
        containerColor = CalorieTrackerTheme.colors.background,
        contentColor = CalorieTrackerTheme.colors.onBackground,
        topBar = {
            TopBarSection(
                onNavigateBack = {
                    onAction(EmailActivationAction.OnNavigateBackClick)
                }
            )
        },
    ) { scaffoldPadding ->
        Box(
            modifier = Modifier
                .padding(scaffoldPadding)
                .padding(horizontal = CalorieTrackerTheme.padding.medium)
                .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            ActivationTextSection(email = state.email)
            BottomSection(
                isLoading = state.isLoading,
                onGoToLoginClicked = {
                    onAction(EmailActivationAction.OnGoToLoginClick)
                },
                onResendEmailClicked = {
                    onAction(EmailActivationAction.OnResendConfirmationEmailClick)
                }
            )
            if (state.isLoading) {
                CircularProgressIndicator(color = CalorieTrackerTheme.colors.primary)
            }
        }
    }
}

@Composable
fun BoxScope.ActivationTextSection(
    email: String,
    textColor: Color = CalorieTrackerTheme.colors.onBackgroundVariant,
    emailColor: Color = CalorieTrackerTheme.colors.primary,
) {
    val text = stringResource(
        R.string.sent_activation_link,
        email
    )

    val annotatedText = remember {
        buildAnnotatedString {
            val startIndex = text.indexOf(email)
            val endIndex = startIndex + email.length
            append(text.substring(0, startIndex))
            withStyle(style = SpanStyle(color = emailColor, fontWeight = FontWeight.Medium)) {
                append(email)
            }
            append(text.substring(endIndex))
        }
    }

    Text(
        modifier = Modifier
            .align(Alignment.Center)
            .fillMaxWidth(),
        text = annotatedText,
        color = textColor,
        style = CalorieTrackerTheme.typography.bodyLarge,
        textAlign = TextAlign.Center
    )
}

@Composable
private fun BoxScope.BottomSection(
    isLoading: Boolean,
    onGoToLoginClicked: () -> Unit,
    onResendEmailClicked: () -> Unit,
) {
    Column(
        modifier = Modifier
            .align(Alignment.BottomCenter)
            .fillMaxWidth()
            .padding(bottom = CalorieTrackerTheme.padding.large),
        horizontalAlignment = Alignment.CenterHorizontally,
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
            enabled = !isLoading,
            modifier = Modifier.fillMaxWidth(),
            onClick = onGoToLoginClicked,
        ) {
            Text(
                text = stringResource(R.string.go_to_login),
                style = CalorieTrackerTheme.typography.titleSmall,
            )
        }
        Spacer(modifier = Modifier.height(CalorieTrackerTheme.spacing.medium))
        TextButton(
            onClick = onResendEmailClicked,
            enabled = !isLoading,
            contentPadding = PaddingValues(CalorieTrackerTheme.padding.tiny)
        ) {
            Text(
                text = stringResource(R.string.resend),
                color = CalorieTrackerTheme.colors.primary,
                style = CalorieTrackerTheme.typography.labelLarge,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBarSection(
    onNavigateBack: () -> Unit,
) {
    CenterAlignedTopAppBar(
        modifier = Modifier.padding(top = CalorieTrackerTheme.padding.small),
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = CalorieTrackerTheme.colors.background,
            titleContentColor = CalorieTrackerTheme.colors.onBackground,
            navigationIconContentColor = CalorieTrackerTheme.colors.onBackground
        ),
        title = {
            Text(
                text = stringResource(R.string.register),
                style = CalorieTrackerTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                color = CalorieTrackerTheme.colors.onBackground
            )
        },
        navigationIcon = {
            IconButton(
                modifier = Modifier,
                onClick = onNavigateBack
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_arrow_back),
                    contentDescription = stringResource(R.string.desc_icon_navigate_back),
                    tint = CalorieTrackerTheme.colors.onBackground,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    )
}