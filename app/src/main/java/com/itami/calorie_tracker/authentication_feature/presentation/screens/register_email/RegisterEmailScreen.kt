package com.itami.calorie_tracker.authentication_feature.presentation.screens.register_email

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.itami.calorie_tracker.R
import com.itami.calorie_tracker.core.domain.model.Theme
import com.itami.calorie_tracker.core.presentation.components.ImagePickerComponent
import com.itami.calorie_tracker.core.presentation.components.ObserveAsEvents
import com.itami.calorie_tracker.core.presentation.components.OutlinedTextField
import com.itami.calorie_tracker.core.presentation.state.PasswordTextFieldState
import com.itami.calorie_tracker.core.presentation.state.StandardTextFieldState
import com.itami.calorie_tracker.core.presentation.theme.CalorieTrackerTheme

@Composable
fun RegisterEmailScreen(
    onEmailRegisterSuccess: (email: String) -> Unit,
    onNavigateBack: () -> Unit,
    onShowSnackbar: (message: String) -> Unit,
    viewModel: RegisterEmailViewModel = hiltViewModel(),
) {
    ObserveAsEvents(viewModel.uiEvent) { event ->
        when (event) {
            is RegisterEmailUiEvent.NavigateBack -> onNavigateBack()
            is RegisterEmailUiEvent.RegisterSuccessful -> onEmailRegisterSuccess(event.registeredEmail)
            is RegisterEmailUiEvent.ShowSnackbar -> onShowSnackbar(event.message)
        }
    }

    RegisterEmailScreenContent(
        state = viewModel.state,
        onAction = viewModel::onAction
    )
}

@Preview
@Composable
fun RegisterEmailScreenContentPreview() {
    CalorieTrackerTheme(theme = Theme.SYSTEM_THEME) {
        RegisterEmailScreenContent(
            state = RegisterEmailState(),
            onAction = {}
        )
    }
}

@Composable
private fun RegisterEmailScreenContent(
    state: RegisterEmailState,
    onAction: (RegisterEmailAction) -> Unit,
) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri -> uri?.let { onAction(RegisterEmailAction.PictureUriChange(it)) } }

    Scaffold(
        containerColor = CalorieTrackerTheme.colors.background,
        contentColor = CalorieTrackerTheme.colors.onBackground,
        topBar = {
            TopBarSection(
                onNavigateBackClick = {
                    onAction(RegisterEmailAction.NavigateBackClick)
                }
            )
        },
    ) { scaffoldPadding ->
        Box(
            modifier = Modifier
                .padding(scaffoldPadding)
                .fillMaxSize()
                .padding(horizontal = CalorieTrackerTheme.padding.medium),
            contentAlignment = Alignment.Center,
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 60.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                ProfilePictureSection(
                    pictureUri = state.profilePictureUri,
                    onAddPhotoClick = {
                        launcher.launch(
                            PickVisualMediaRequest(
                                mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
                            )
                        )
                    }
                )
                Spacer(modifier = Modifier.height(60.dp))
                CredentialsSection(
                    modifier = Modifier.fillMaxWidth(),
                    nameState = state.nameState,
                    onNameChange = {
                        onAction(RegisterEmailAction.NameInputChange(it))
                    },
                    emailState = state.emailState,
                    onEmailChange = {
                        onAction(RegisterEmailAction.EmailInputChange(it))
                    },
                    passwordState = state.passwordState,
                    onPasswordChange = {
                        onAction(RegisterEmailAction.PasswordInputChange(it))
                    },
                    onPasswordVisibilityIconClick = {
                        onAction(RegisterEmailAction.PasswordVisibilityIconClick)
                    },
                    isLoading = state.isLoading
                )
            }
            RegisterButtonSection(
                isLoading = state.isLoading,
                onRegisterClick = {
                    onAction(RegisterEmailAction.RegisterClick)
                }
            )
            if (state.isLoading) {
                CircularProgressIndicator(color = CalorieTrackerTheme.colors.primary)
            }
        }
    }
}

@Composable
private fun ProfilePictureSection(
    pictureUri: Uri?,
    onAddPhotoClick: () -> Unit,
) {
    ImagePickerComponent(
        imageSize = 124.dp,
        imagePath = pictureUri,
        onAddImageButtonClicked = onAddPhotoClick
    )
}

@Composable
private fun CredentialsSection(
    modifier: Modifier,
    nameState: StandardTextFieldState,
    onNameChange: (String) -> Unit,
    emailState: StandardTextFieldState,
    onEmailChange: (String) -> Unit,
    passwordState: PasswordTextFieldState,
    onPasswordChange: (String) -> Unit,
    onPasswordVisibilityIconClick: () -> Unit,
    isLoading: Boolean,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(CalorieTrackerTheme.spacing.medium)
    ) {
        OutlinedTextField(
            value = nameState.text,
            onValueChange = onNameChange,
            enabled = !isLoading,
            label = stringResource(R.string.label_name),
            error = nameState.errorMessage,
        )
        OutlinedTextField(
            value = emailState.text,
            onValueChange = onEmailChange,
            enabled = !isLoading,
            label = stringResource(R.string.label_email),
            error = emailState.errorMessage,
            keyboardType = KeyboardType.Email
        )
        OutlinedTextField(
            value = passwordState.text,
            onValueChange = onPasswordChange,
            enabled = !isLoading,
            label = stringResource(R.string.label_password),
            error = passwordState.errorMessage,
            keyboardType = KeyboardType.Password,
            visualTransformation = if (passwordState.isPasswordVisible) VisualTransformation.None
            else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = onPasswordVisibilityIconClick) {
                    Icon(
                        painter = painterResource(
                            id = if (passwordState.isPasswordVisible) R.drawable.icon_visibility
                            else R.drawable.icon_visibility_off
                        ),
                        contentDescription = stringResource(R.string.desc_visibility_icon)
                    )
                }
            }
        )
    }
}

@Composable
private fun BoxScope.RegisterButtonSection(
    isLoading: Boolean,
    onRegisterClick: () -> Unit,
) {
    Button(
        onClick = onRegisterClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = CalorieTrackerTheme.colors.primary,
            contentColor = CalorieTrackerTheme.colors.onPrimary,
        ),
        contentPadding = PaddingValues(vertical = CalorieTrackerTheme.padding.default),
        shape = CalorieTrackerTheme.shapes.small,
        enabled = !isLoading,
        modifier = Modifier
            .align(Alignment.BottomCenter)
            .fillMaxWidth()
            .padding(bottom = CalorieTrackerTheme.padding.extraLarge),
    ) {
        Text(
            text = stringResource(R.string.register),
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
                text = stringResource(R.string.register),
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