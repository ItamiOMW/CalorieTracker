package com.itami.calorie_tracker.settings_feature.presentation.screens.account

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.itami.calorie_tracker.R
import com.itami.calorie_tracker.core.domain.model.Theme
import com.itami.calorie_tracker.core.domain.model.User
import com.itami.calorie_tracker.core.presentation.components.DialogComponent
import com.itami.calorie_tracker.core.presentation.components.ObserveAsEvents
import com.itami.calorie_tracker.core.presentation.components.OptionItem
import com.itami.calorie_tracker.core.presentation.theme.CalorieTrackerTheme

@Composable
fun AccountScreen(
    onLogoutSuccessful: () -> Unit,
    onDeleteAccountSuccessful: () -> Unit,
    onNavigateToChangePassword: () -> Unit,
    onNavigateBack: () -> Unit,
    onShowSnackbar: (message: String) -> Unit,
    viewModel: AccountViewModel = hiltViewModel(),
) {
    ObserveAsEvents(viewModel.uiEvent) { event ->
        when (event) {
            AccountUiEvent.DeleteAccountSuccessful -> onDeleteAccountSuccessful()
            AccountUiEvent.LogoutSuccessful -> onLogoutSuccessful()
            AccountUiEvent.NavigateBack -> onNavigateBack()
            AccountUiEvent.NavigateToChangePassword -> onNavigateToChangePassword()
            is AccountUiEvent.ShowSnackbar -> onShowSnackbar(event.message)
        }
    }

    AccountScreenContent(
        state = viewModel.state,
        onAction = viewModel::onAction,
    )
}

@Preview
@Composable
private fun AccountScreenContentPreview() {
    CalorieTrackerTheme(theme = Theme.SYSTEM_THEME) {
        AccountScreenContent(
            state = AccountState(),
            onAction = {}
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AccountScreenContent(
    state: AccountState,
    onAction: (AccountAction) -> Unit,
) {
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    if (state.showNameSheetContent) {
        ModalBottomSheet(
            sheetState = bottomSheetState,
            containerColor = CalorieTrackerTheme.colors.surfacePrimary,
            contentColor = CalorieTrackerTheme.colors.onSurfacePrimary,
            onDismissRequest = { onAction(AccountAction.DismissBottomSheet) },
        ) {
            NameSheetContent(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = CalorieTrackerTheme.padding.default,
                        end = CalorieTrackerTheme.padding.default,
                        bottom = CalorieTrackerTheme.padding.large
                    ),
                initialName = state.newName ?: state.user.name,
                onConfirm = { age -> onAction(AccountAction.ChangeName(age)) }
            )
        }
    }

    if (state.showLogoutDialog) {
        DialogComponent(
            title = stringResource(id = R.string.logout),
            description = stringResource(R.string.you_sure_you_want_to_log_out),
            cancelText = stringResource(id = R.string.cancel),
            confirmText = stringResource(R.string.log_out),
            onConfirm = { onAction(AccountAction.ConfirmLogout) },
            onDismiss = { onAction(AccountAction.DenyLogout) },
            confirmTextColor = CalorieTrackerTheme.colors.danger
        )
    }

    if (state.showDeleteAccountDialog) {
        DialogComponent(
            title = stringResource(id = R.string.delete_account),
            description = stringResource(R.string.you_sure_you_want_to_delete_account),
            cancelText = stringResource(id = R.string.cancel),
            confirmText = stringResource(R.string.delete),
            onConfirm = { onAction(AccountAction.ConfirmDeleteAccount) },
            onDismiss = { onAction(AccountAction.DenyDeleteAccount) },
            confirmTextColor = CalorieTrackerTheme.colors.danger
        )
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri -> uri?.let { onAction(AccountAction.ChangePictureUri(it)) } }

    Scaffold(
        containerColor = CalorieTrackerTheme.colors.background,
        contentColor = CalorieTrackerTheme.colors.onBackground,
        topBar = {
            TopBarSection(onNavigateBackClick = { onAction(AccountAction.NavigateBackClick) })
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
                onClick = { onAction(AccountAction.SaveClick) }
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
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.height(CalorieTrackerTheme.spacing.default))
                ProfilePictureSection(
                    user = state.user,
                    newPictureUri = state.newProfilePictureUri,
                    modifier = Modifier
                        .padding(horizontal = CalorieTrackerTheme.padding.large)
                        .wrapContentWidth()
                )
                Spacer(modifier = Modifier.height(CalorieTrackerTheme.spacing.large))
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    OptionItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp),
                        optionText = stringResource(id = R.string.label_name),
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.icon_person),
                                contentDescription = stringResource(R.string.desc_person_icon),
                                tint = if (CalorieTrackerTheme.isDarkTheme) CalorieTrackerTheme.colors.onBackground
                                else CalorieTrackerTheme.colors.primary,
                                modifier = Modifier.size(24.dp),
                            )
                        },
                        trailingContent = {
                            Text(
                                text = state.newName ?: state.user.name,
                                color = CalorieTrackerTheme.colors.primary,
                                style = CalorieTrackerTheme.typography.bodyMedium
                            )
                        },
                        onClick = { onAction(AccountAction.NameClick) }
                    )
                    OptionItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp),
                        optionText = stringResource(R.string.change_profile_picture),
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.icon_add_photo),
                                contentDescription = stringResource(R.string.desc_add_photo_icon),
                                tint = if (CalorieTrackerTheme.isDarkTheme) CalorieTrackerTheme.colors.onBackground
                                else CalorieTrackerTheme.colors.primary,
                                modifier = Modifier.size(24.dp),
                            )
                        },
                        onClick = {
                            launcher.launch(
                                PickVisualMediaRequest(
                                    mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
                                )
                            )
                        }
                    )
                }
                Spacer(modifier = Modifier.height(CalorieTrackerTheme.spacing.default))
                HorizontalDivider(
                    modifier = Modifier
                        .padding(horizontal = CalorieTrackerTheme.padding.small)
                        .fillMaxWidth(),
                    color = CalorieTrackerTheme.colors.outlineVariant
                )
                Spacer(modifier = Modifier.height(CalorieTrackerTheme.spacing.default))
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    OptionItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp),
                        optionText = stringResource(R.string.change_password),
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.icon_lock_reset),
                                contentDescription = stringResource(R.string.desc_lock_reset_icon),
                                tint = if (CalorieTrackerTheme.isDarkTheme) CalorieTrackerTheme.colors.onBackground
                                else CalorieTrackerTheme.colors.primary,
                                modifier = Modifier.size(24.dp),
                            )
                        },
                        onClick = { onAction(AccountAction.ChangePasswordClick) }
                    )
                    OptionItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp),
                        optionText = stringResource(R.string.logout),
                        textColor = CalorieTrackerTheme.colors.danger,
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.icon_logout),
                                contentDescription = stringResource(R.string.logout),
                                tint = CalorieTrackerTheme.colors.danger,
                                modifier = Modifier.size(24.dp),
                            )
                        },
                        onClick = { onAction(AccountAction.LogoutClick) }
                    )
                    OptionItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp),
                        optionText = stringResource(R.string.delete_account),
                        textColor = CalorieTrackerTheme.colors.danger,
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.icon_delete),
                                contentDescription = stringResource(R.string.desc_delete_icon),
                                tint = CalorieTrackerTheme.colors.danger,
                                modifier = Modifier.size(24.dp),
                            )
                        },
                        onClick = { onAction(AccountAction.DeleteAccountClick) }
                    )
                }
            }
            if (state.isLoading) {
                CircularProgressIndicator(color = CalorieTrackerTheme.colors.primary)
            }
        }
    }
}

@Composable
private fun ProfilePictureSection(
    modifier: Modifier,
    newPictureUri: Uri?,
    user: User,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AsyncImage(
            model = newPictureUri ?: user.profilePictureUrl,
            contentDescription = stringResource(R.string.desc_user_profile_picture),
            error = painterResource(id = R.drawable.unknown_person),
            placeholder = painterResource(id = R.drawable.unknown_person),
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .size(90.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.height(CalorieTrackerTheme.spacing.default))
        Text(
            text = user.email,
            style = CalorieTrackerTheme.typography.titleSmall,
            color = if (CalorieTrackerTheme.isDarkTheme) CalorieTrackerTheme.colors.secondary
            else CalorieTrackerTheme.colors.primary,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun NameSheetContent(
    modifier: Modifier = Modifier,
    initialName: String = "",
    onConfirm: (name: String) -> Unit,
) {
    var nameState by remember { mutableStateOf(initialName) }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(id = R.string.label_name),
            style = CalorieTrackerTheme.typography.titleMedium,
            color = CalorieTrackerTheme.colors.onSurfacePrimary,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(CalorieTrackerTheme.spacing.large))
        TextField(
            modifier = Modifier.fillMaxWidth(0.5f),
            value = nameState,
            onValueChange = { newValue ->
                nameState = newValue
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
            singleLine = true,
        )
        Spacer(modifier = Modifier.height(CalorieTrackerTheme.spacing.large))
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = CalorieTrackerTheme.colors.primary,
                contentColor = CalorieTrackerTheme.colors.onPrimary,
            ),
            contentPadding = PaddingValues(vertical = CalorieTrackerTheme.padding.default),
            shape = CalorieTrackerTheme.shapes.small,
            modifier = Modifier.fillMaxWidth(),
            onClick = { onConfirm(nameState) }
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
            text = stringResource(R.string.save),
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
                text = stringResource(R.string.account),
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



