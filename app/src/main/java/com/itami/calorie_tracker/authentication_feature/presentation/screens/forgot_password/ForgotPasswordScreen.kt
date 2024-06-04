package com.itami.calorie_tracker.authentication_feature.presentation.screens.forgot_password

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
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.itami.calorie_tracker.R
import com.itami.calorie_tracker.core.domain.model.Theme
import com.itami.calorie_tracker.core.presentation.components.ObserveAsEvents
import com.itami.calorie_tracker.core.presentation.components.OutlinedTextField
import com.itami.calorie_tracker.core.presentation.state.StandardTextFieldState
import com.itami.calorie_tracker.core.presentation.theme.CalorieTrackerTheme

@Composable
fun ForgotPasswordScreen(
    onResetCodeSent: (email: String) -> Unit,
    onNavigateBack: () -> Unit,
    onShowSnackbar: (message: String) -> Unit,
    viewModel: ForgotPasswordViewModel = hiltViewModel(),
) {
    ObserveAsEvents(viewModel.uiEvent) { event ->
        when (event) {
            is ForgotPasswordUiEvent.ResetCodeSentSuccessfully -> onResetCodeSent(event.email)
            is ForgotPasswordUiEvent.ShowSnackbar -> onShowSnackbar(event.message)
            is ForgotPasswordUiEvent.NavigateBack -> onNavigateBack()
        }
    }

    ForgotPasswordScreenContent(
        state = viewModel.state,
        onAction = viewModel::onAction
    )
}

@Preview
@Composable
fun ForgotPasswordScreenContentPreview() {
    CalorieTrackerTheme(theme = Theme.SYSTEM_THEME) {
        ForgotPasswordScreenContent(
            state = ForgotPasswordState(),
            onAction = {}
        )
    }
}

@Composable
private fun ForgotPasswordScreenContent(
    state: ForgotPasswordState,
    onAction: (ForgotPasswordAction) -> Unit,
) {
    Scaffold(
        containerColor = CalorieTrackerTheme.colors.background,
        contentColor = CalorieTrackerTheme.colors.onBackground,
        topBar = {
            TopBarSection(
                onNavigateBack = {
                    onAction(ForgotPasswordAction.NavigateBackClick)
                }
            )
        },
    ) { scaffoldPadding ->
        Box(
            modifier = Modifier
                .padding(scaffoldPadding)
                .padding(horizontal = CalorieTrackerTheme.padding.medium)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            EmailSection(
                emailState = state.emailState,
                isLoading = state.isLoading,
                onEmailChange = {
                    onAction(ForgotPasswordAction.EmailInputChange(it))
                }
            )
            SendCodeButtonSection(
                isLoading = state.isLoading,
                onSendClick = {
                    onAction(ForgotPasswordAction.SendResetCodeClick)
                }
            )
            if (state.isLoading) {
                CircularProgressIndicator(color = CalorieTrackerTheme.colors.primary)
            }
        }
    }
}

@Composable
private fun BoxScope.EmailSection(
    emailState: StandardTextFieldState,
    isLoading: Boolean,
    onEmailChange: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .align(Alignment.Center)
            .padding(bottom = CalorieTrackerTheme.padding.large)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.we_will_send_reset_code),
            style = CalorieTrackerTheme.typography.bodyLarge,
            color = CalorieTrackerTheme.colors.onBackgroundVariant,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(CalorieTrackerTheme.spacing.large))
        OutlinedTextField(
            value = emailState.text,
            onValueChange = onEmailChange,
            enabled = !isLoading,
            label = stringResource(R.string.label_email),
            error = emailState.errorMessage,
            keyboardType = KeyboardType.Email
        )
    }
}

@Composable
private fun BoxScope.SendCodeButtonSection(
    isLoading: Boolean,
    onSendClick: () -> Unit,
) {
    Button(
        modifier = Modifier
            .align(Alignment.BottomCenter)
            .fillMaxWidth()
            .padding(bottom = CalorieTrackerTheme.padding.extraLarge),
        colors = ButtonDefaults.buttonColors(
            containerColor = CalorieTrackerTheme.colors.primary,
            contentColor = CalorieTrackerTheme.colors.onPrimary,
        ),
        contentPadding = PaddingValues(vertical = CalorieTrackerTheme.padding.default),
        shape = CalorieTrackerTheme.shapes.small,
        enabled = !isLoading,
        onClick = onSendClick,
    ) {
        Text(
            text = stringResource(R.string.send_code),
            style = CalorieTrackerTheme.typography.titleSmall,
        )
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
                text = stringResource(R.string.title_forgot_password),
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