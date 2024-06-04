package com.itami.calorie_tracker.authentication_feature.presentation.screens.reset_password_success

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.itami.calorie_tracker.R
import com.itami.calorie_tracker.core.domain.model.Theme
import com.itami.calorie_tracker.core.presentation.theme.CalorieTrackerTheme

@Composable
fun ResetPasswordSuccessScreen(
    onNavigateToLogin: () -> Unit,
    onNavigateBack: () -> Unit,
) {
    ResetPasswordSuccessScreenContent(
        onNavigateToLogin = onNavigateToLogin,
        onNavigateBack = onNavigateBack
    )
}

@Preview
@Composable
fun ResetPasswordSuccessScreenContentPreview() {
    CalorieTrackerTheme(theme = Theme.SYSTEM_THEME) {
        ResetPasswordSuccessScreenContent(
            onNavigateToLogin = {},
            onNavigateBack = {}
        )
    }
}

@Composable
private fun ResetPasswordSuccessScreenContent(
    onNavigateToLogin: () -> Unit,
    onNavigateBack: () -> Unit,
) {
    Scaffold(
        containerColor = CalorieTrackerTheme.colors.background,
        contentColor = CalorieTrackerTheme.colors.onBackground,
        topBar = {
            TopBarSection(onNavigateBackClick = onNavigateBack)
        }
    ) { scaffoldPadding ->
        Box(
            modifier = Modifier
                .padding(scaffoldPadding)
                .padding(horizontal = CalorieTrackerTheme.padding.medium)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            TextSection()
            GoToLoginButtonSection(onGoToLoginClick = onNavigateToLogin)
        }
    }
}

@Composable
private fun BoxScope.TextSection() {
    Text(
        modifier = Modifier
            .align(Alignment.Center)
            .padding(bottom = CalorieTrackerTheme.padding.medium),
        text = stringResource(R.string.you_successfully_changed_password),
        style = CalorieTrackerTheme.typography.bodyLarge,
        color = CalorieTrackerTheme.colors.onBackgroundVariant,
        textAlign = TextAlign.Center
    )
}

@Composable
private fun BoxScope.GoToLoginButtonSection(
    onGoToLoginClick: () -> Unit,
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
        contentPadding = PaddingValues(
            vertical = CalorieTrackerTheme.padding.default
        ),
        shape = CalorieTrackerTheme.shapes.small,
        onClick = onGoToLoginClick,
    ) {
        Text(
            text = stringResource(R.string.go_to_login),
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
                text = stringResource(R.string.title_forgot_password),
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