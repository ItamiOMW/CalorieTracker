package com.itami.calorie_tracker.settings_feature.presentation.screens.change_password

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.itami.calorie_tracker.R
import com.itami.calorie_tracker.core.domain.model.Theme
import com.itami.calorie_tracker.core.presentation.components.ObserveAsEvents
import com.itami.calorie_tracker.core.presentation.components.OutlinedTextField
import com.itami.calorie_tracker.core.presentation.theme.CalorieTrackerTheme

@Composable
fun ChangePasswordScreen(
    onPasswordChanged: () -> Unit,
    onNavigateBack: () -> Unit,
    onShowSnackbar: (message: String) -> Unit,
    viewModel: ChangePasswordViewModel = hiltViewModel(),
) {
    ObserveAsEvents(viewModel.uiEvent) { event ->
        when (event) {
            ChangePasswordUiEvent.NavigateBack -> onNavigateBack()
            ChangePasswordUiEvent.PasswordChanged -> onPasswordChanged()
            is ChangePasswordUiEvent.ShowSnackbar -> onShowSnackbar(event.message)
        }
    }

    ChangePasswordScreenContent(
        state = viewModel.state,
        onAction = viewModel::onAction
    )
}

@Preview
@Composable
private fun ChangePasswordScreenContentPreview() {
    CalorieTrackerTheme(theme = Theme.SYSTEM_THEME) {
        ChangePasswordScreenContent(
            state = ChangePasswordState(),
            onAction = { }
        )
    }
}

@Composable
private fun ChangePasswordScreenContent(
    state: ChangePasswordState,
    onAction: (ChangePasswordAction) -> Unit,
) {
    Scaffold(
        containerColor = CalorieTrackerTheme.colors.background,
        contentColor = CalorieTrackerTheme.colors.onBackground,
        topBar = {
            TopBarSection(onNavigateBackClick = { onAction(ChangePasswordAction.NavigateBackClick) })
        },
        bottomBar = {
            ChangePasswordButtonSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = CalorieTrackerTheme.padding.default,
                        end = CalorieTrackerTheme.padding.default,
                        bottom = CalorieTrackerTheme.padding.large,
                    ),
                isLoading = state.isLoading,
                onClick = { onAction(ChangePasswordAction.ChangeClick) }
            )
        }
    ) { scaffoldPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(scaffoldPadding),
            contentAlignment = Alignment.Center,
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = CalorieTrackerTheme.padding.default)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(CalorieTrackerTheme.spacing.medium)
            ) {
                OutlinedTextField(
                    value = state.oldPassword.text,
                    onValueChange = { onAction(ChangePasswordAction.OldPasswordChange(it)) },
                    enabled = !state.isLoading,
                    label = stringResource(R.string.label_old_password),
                    error = state.oldPassword.errorMessage,
                    keyboardType = KeyboardType.Password,
                    visualTransformation = if (state.oldPassword.isPasswordVisible) VisualTransformation.None
                    else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(
                            onClick = { onAction(ChangePasswordAction.ChangeOldPasswordVisibility) }
                        ) {
                            Icon(
                                painter = painterResource(
                                    id = if (state.oldPassword.isPasswordVisible) R.drawable.icon_visibility
                                    else R.drawable.icon_visibility_off
                                ),
                                contentDescription = stringResource(R.string.desc_visibility_icon)
                            )
                        }
                    }
                )
                OutlinedTextField(
                    value = state.newPassword.text,
                    onValueChange = { onAction(ChangePasswordAction.NewPasswordChange(it)) },
                    enabled = !state.isLoading,
                    label = stringResource(R.string.label_new_password),
                    error = state.newPassword.errorMessage,
                    keyboardType = KeyboardType.Password,
                    visualTransformation = if (state.newPassword.isPasswordVisible) VisualTransformation.None
                    else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(
                            onClick = { onAction(ChangePasswordAction.ChangeNewPasswordVisibility) }
                        ) {
                            Icon(
                                painter = painterResource(
                                    id = if (state.newPassword.isPasswordVisible) R.drawable.icon_visibility
                                    else R.drawable.icon_visibility_off
                                ),
                                contentDescription = stringResource(R.string.desc_visibility_icon)
                            )
                        }
                    }
                )
                OutlinedTextField(
                    value = state.repeatNewPassword.text,
                    onValueChange = { onAction(ChangePasswordAction.RepeatNewPasswordChange(it)) },
                    enabled = !state.isLoading,
                    label = stringResource(R.string.label_repeat_new_password),
                    error = state.repeatNewPassword.errorMessage,
                    keyboardType = KeyboardType.Password,
                    visualTransformation = if (state.repeatNewPassword.isPasswordVisible) VisualTransformation.None
                    else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(
                            onClick = { onAction(ChangePasswordAction.ChangeRepeatNewPasswordVisibility) }
                        ) {
                            Icon(
                                painter = painterResource(
                                    id = if (state.repeatNewPassword.isPasswordVisible) R.drawable.icon_visibility
                                    else R.drawable.icon_visibility_off
                                ),
                                contentDescription = stringResource(R.string.desc_visibility_icon)
                            )
                        }
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
private fun ChangePasswordButtonSection(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    onClick: () -> Unit,
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
        modifier = modifier,
        onClick = onClick,
    ) {
        Text(
            text = stringResource(R.string.change_password),
            style = CalorieTrackerTheme.typography.titleSmall,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBarSection(
    onNavigateBackClick: () -> Unit,
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
                text = stringResource(R.string.change_password),
                style = CalorieTrackerTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                color = CalorieTrackerTheme.colors.onBackground
            )
        },
        navigationIcon = {
            IconButton(
                modifier = Modifier,
                onClick = onNavigateBackClick
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
