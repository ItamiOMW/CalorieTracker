package com.itami.calorie_tracker.profile_feature.presentation.screens.contact_us

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
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
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
import com.itami.calorie_tracker.core.presentation.components.IconToText
import com.itami.calorie_tracker.core.presentation.components.ObserveAsEvents
import com.itami.calorie_tracker.core.presentation.components.OutlinedTextField
import com.itami.calorie_tracker.core.presentation.state.StandardTextFieldState
import com.itami.calorie_tracker.core.presentation.theme.CalorieTrackerTheme

@Composable
fun ContactUsScreen(
    onNavigateToEmail: (email: String) -> Unit,
    onNavigateToTelegram: (username: String) -> Unit,
    onNavigateToGithub: (username: String) -> Unit,
    onNavigateBack: () -> Unit,
    onShowSnackbar: (message: String) -> Unit,
    viewModel: ContactUsViewModel = hiltViewModel(),
) {
    ObserveAsEvents(viewModel.uiEvent) { event ->
        when (event) {
            is ContactUsUiEvent.NavigateBack -> onNavigateBack()
            is ContactUsUiEvent.ShowSnackbar -> onShowSnackbar(event.message)
            is ContactUsUiEvent.NavigateToEmail -> onNavigateToEmail(event.email)
            is ContactUsUiEvent.NavigateToGithub -> onNavigateToGithub(event.githubUsername)
            is ContactUsUiEvent.NavigateToTelegram -> onNavigateToTelegram(event.telegramUsername)
        }
    }

    ContactUsScreenContent(
        state = viewModel.state,
        onAction = viewModel::onAction
    )
}

@Preview
@Composable
fun ContactUsScreenContentPreview() {
    CalorieTrackerTheme(theme = Theme.SYSTEM_THEME) {
        ContactUsScreenContent(
            state = ContactUsState(),
            onAction = {}
        )
    }
}

@Composable
private fun ContactUsScreenContent(
    state: ContactUsState,
    onAction: (ContactUsAction) -> Unit,
) {
    Scaffold(
        containerColor = CalorieTrackerTheme.colors.background,
        contentColor = CalorieTrackerTheme.colors.onBackground,
        topBar = {
            TopBarSection(
                onNavigateBackClick = {
                    onAction(ContactUsAction.NavigateBackClick)
                }
            )
        },
        bottomBar = {
            SocialLinksSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = CalorieTrackerTheme.padding.large),
                onEmailClick = { onAction(ContactUsAction.EmailLinkClick) },
                onTelegramClick = { onAction(ContactUsAction.TelegramLinkClick) },
                onGithubClick = { onAction(ContactUsAction.GithubLinkClick) }
            )
        }
    ) { scaffoldPadding ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(scaffoldPadding),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .padding(top = CalorieTrackerTheme.padding.large)
                    .padding(horizontal = CalorieTrackerTheme.padding.default)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                ContactUsTextSection(modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(CalorieTrackerTheme.spacing.large))
                ContactInfoSection(
                    modifier = Modifier.fillMaxWidth(),
                    messageState = state.messageState,
                    isLoading = state.isLoading,
                    onMessageValueChange = { onAction(ContactUsAction.MessageValueChange(it)) },
                    onSendButtonClick = { onAction(ContactUsAction.SendMessageClick) }
                )
            }
            if (state.isLoading) {
                CircularProgressIndicator(color = CalorieTrackerTheme.colors.primary)
            }
        }
    }
}

@Composable
private fun ContactInfoSection(
    modifier: Modifier = Modifier,
    messageState: StandardTextFieldState,
    isLoading: Boolean,
    onMessageValueChange: (newValue: String) -> Unit,
    onSendButtonClick: () -> Unit,
) {
    Surface(
        modifier = modifier,
        color = CalorieTrackerTheme.colors.surfacePrimary,
        contentColor = CalorieTrackerTheme.colors.onSurfacePrimary,
        shape = CalorieTrackerTheme.shapes.small,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = CalorieTrackerTheme.padding.default,
                    end = CalorieTrackerTheme.padding.default,
                    top = CalorieTrackerTheme.padding.default,
                    bottom = CalorieTrackerTheme.padding.medium,
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(R.string.contact_info),
                color = CalorieTrackerTheme.colors.onSurfacePrimary,
                style = CalorieTrackerTheme.typography.labelLarge,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(CalorieTrackerTheme.spacing.default))
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(CalorieTrackerTheme.spacing.small)
            ) {
                IconToText(
                    text = stringResource(id = R.string.developer_email_address),
                    iconPainter = painterResource(id = R.drawable.icon_mail),
                    iconColor = CalorieTrackerTheme.colors.primary,
                    textColor = CalorieTrackerTheme.colors.onSurfacePrimary
                )
                IconToText(
                    text = "@${stringResource(id = R.string.developer_telegram_username)}",
                    iconPainter = painterResource(id = R.drawable.icon_telegram),
                    iconColor = CalorieTrackerTheme.colors.primary,
                    textColor = CalorieTrackerTheme.colors.onSurfacePrimary
                )
            }
            Spacer(modifier = Modifier.height(CalorieTrackerTheme.spacing.extraLarge))
            OutlinedTextField(
                value = messageState.text,
                error = messageState.errorMessage,
                onValueChange = onMessageValueChange,
                singleLine = false,
                maxLines = 6,
                label = stringResource(R.string.message),
            )
            Spacer(modifier = Modifier.height(CalorieTrackerTheme.spacing.large))
            Button(
                onClick = onSendButtonClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = CalorieTrackerTheme.colors.primary,
                    contentColor = CalorieTrackerTheme.colors.onPrimary,
                ),
                enabled = !isLoading,
                shape = CalorieTrackerTheme.shapes.small,
                contentPadding = PaddingValues(
                    vertical = CalorieTrackerTheme.padding.small,
                    horizontal = CalorieTrackerTheme.padding.large
                )
            ) {
                Text(
                    text = stringResource(R.string.send),
                    style = CalorieTrackerTheme.typography.labelLarge,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

@Composable
private fun ContactUsTextSection(
    modifier: Modifier = Modifier,
    appNameColor: Color = CalorieTrackerTheme.colors.primary,
    appName: String = stringResource(id = R.string.app_name),
) {
    val annotatedString = remember {
        buildAnnotatedString {
            append("Don’t hesitate to contact us if you find a bug or have a suggestion. We highly appreciate any feedback provided, as it helps us improve your ")
            withStyle(style = SpanStyle(color = appNameColor, fontWeight = FontWeight.Medium)) {
                append(appName)
            }
            append(".")
        }
    }

    Text(
        modifier = modifier,
        text = annotatedString,
        color = CalorieTrackerTheme.colors.onBackgroundVariant,
        style = CalorieTrackerTheme.typography.bodyLarge,
        textAlign = TextAlign.Center,
    )
}

@Composable
private fun SocialLinksSection(
    modifier: Modifier = Modifier,
    onEmailClick: () -> Unit,
    onTelegramClick: () -> Unit,
    onGithubClick: () -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(CalorieTrackerTheme.spacing.small)
    ) {
        Text(
            text = stringResource(R.string.our_social_links),
            style = CalorieTrackerTheme.typography.bodyMedium,
            color = CalorieTrackerTheme.colors.onBackgroundVariant,
            textAlign = TextAlign.Center,
        )
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(CalorieTrackerTheme.spacing.medium)
        ) {
            SocialLinkIcon(
                iconPainter = painterResource(id = R.drawable.icon_mail),
                description = stringResource(R.string.desc_email_social_link),
                onClick = onEmailClick
            )
            SocialLinkIcon(
                iconPainter = painterResource(id = R.drawable.icon_telegram),
                description = stringResource(R.string.desc_telegram_social_link),
                onClick = onTelegramClick
            )
            SocialLinkIcon(
                iconPainter = painterResource(id = R.drawable.icon_github),
                description = stringResource(R.string.desc_github_social_link),
                onClick = onGithubClick
            )
        }
    }
}

@Composable
private fun SocialLinkIcon(
    iconPainter: Painter,
    description: String,
    iconColor: Color = CalorieTrackerTheme.colors.primary,
    onClick: () -> Unit,
) {
    IconButton(
        onClick = onClick
    ) {
        Icon(
            painter = iconPainter,
            contentDescription = description,
            tint = iconColor,
            modifier = Modifier.size(32.dp)
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
                text = stringResource(R.string.title_contact_us),
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