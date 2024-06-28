package com.itami.calorie_tracker.settings_feature.presentation.screens.language

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.itami.calorie_tracker.R
import com.itami.calorie_tracker.core.domain.model.Theme
import com.itami.calorie_tracker.core.presentation.components.ObserveAsEvents
import com.itami.calorie_tracker.core.presentation.theme.CalorieTrackerTheme
import com.itami.calorie_tracker.core.utils.capitalizeFirstLetter

@Composable
fun LanguageScreen(
    onNavigateBack: () -> Unit,
    viewModel: LanguageViewModel = hiltViewModel(),
) {
    ObserveAsEvents(viewModel.uiEvent) { event ->
        when(event) {
            LanguageUiEvent.NavigateBack -> onNavigateBack()
        }
    }

    LanguageScreenContent(
        state = viewModel.state,
        onAction = viewModel::onAction
    )
}

@Preview
@Composable
fun LanguageScreenContentPreview() {
    CalorieTrackerTheme(theme = Theme.SYSTEM_THEME) {
        LanguageScreenContent(
            state = LanguageState(),
            onAction = {}
        )
    }
}

@Composable
private fun LanguageScreenContent(
    state: LanguageState,
    onAction: (LanguageAction) -> Unit,
) {
    Scaffold(
        containerColor = CalorieTrackerTheme.colors.background,
        contentColor = CalorieTrackerTheme.colors.onBackground,
        topBar = {
            TopBarSection(onNavigateBackClick = { onAction(LanguageAction.NavigateBackClick) })
        },
    ) { scaffoldPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(scaffoldPadding),
            contentAlignment = Alignment.Center,
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(top = CalorieTrackerTheme.padding.large)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                items(state.locales, key = { it.displayLanguage }) { locale ->
                    LanguageItem(
                        modifier = Modifier.fillMaxWidth(),
                        name = locale.getDisplayName(locale),
                        translatedName = locale.getDisplayName(state.selectedLocale),
                        selected = locale.language == state.selectedLocale.language,
                        onClick = { onAction(LanguageAction.LanguageClick(locale)) },
                    )
                }
            }
        }
    }
}

@Composable
private fun LanguageItem(
    modifier: Modifier = Modifier,
    name: String,
    translatedName: String,
    selected: Boolean,
    contentPaddingValues: PaddingValues = PaddingValues(
        horizontal = CalorieTrackerTheme.padding.small,
        vertical = CalorieTrackerTheme.padding.small
    ),
    onClick: () -> Unit,
) {
    Surface(
        modifier = modifier,
        color = Color.Transparent,
        contentColor = CalorieTrackerTheme.colors.onBackground,
        onClick = onClick,
    ) {
        Row(
            modifier = Modifier.padding(contentPaddingValues),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            RadioButton(
                selected = selected,
                colors = RadioButtonDefaults.colors(
                    selectedColor = CalorieTrackerTheme.colors.primary,
                    unselectedColor = CalorieTrackerTheme.colors.outlineVariant,
                ),
                onClick = onClick
            )
            Spacer(modifier = Modifier.width(CalorieTrackerTheme.spacing.default))
            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.spacedBy(CalorieTrackerTheme.spacing.tiny),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = name.capitalizeFirstLetter(),
                    style = CalorieTrackerTheme.typography.bodyLarge,
                    color = CalorieTrackerTheme.colors.onBackground
                )
                Text(
                    text = translatedName.capitalizeFirstLetter(),
                    style = CalorieTrackerTheme.typography.bodyMedium,
                    color = CalorieTrackerTheme.colors.onBackgroundVariant
                )
            }
        }
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
                text = stringResource(R.string.language),
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