package com.itami.calorie_tracker.core.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.itami.calorie_tracker.core.presentation.theme.CalorieTrackerTheme

@Composable
fun OtpTextField(
    modifier: Modifier = Modifier,
    otpText: String,
    otpCount: Int = 6,
    enabled: Boolean = true,
    onOtpTextChange: (String) -> Unit,
) {
    LaunchedEffect(Unit) {
        if (otpText.length > otpCount) {
            throw IllegalArgumentException("Otp text value must not have more than otpCount: $otpCount characters")
        }
    }

    BasicTextField(
        modifier = modifier,
        value = TextFieldValue(otpText, selection = TextRange(otpText.length)),
        onValueChange = { newValue ->
            if (newValue.text.length <= otpCount) {
                onOtpTextChange.invoke(newValue.text)
            }
        },
        cursorBrush = SolidColor(CalorieTrackerTheme.colors.primary),
        enabled = enabled,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
        decorationBox = {
            Row(horizontalArrangement = Arrangement.Center) {
                repeat(otpCount) { index ->
                    CharView(
                        index = index,
                        text = otpText
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        }
    )
}

@Composable
private fun CharView(
    index: Int,
    text: String,
) {
    val isFocused = text.length == index
    val char = when {
        index == text.length -> "0"
        index > text.length -> ""
        else -> text[index].toString()
    }
    Text(
        modifier = Modifier
            .size(35.dp, 41.dp)
            .border(
                1.5.dp, when {
                    isFocused -> CalorieTrackerTheme.colors.primary
                    else -> CalorieTrackerTheme.colors.onBackgroundVariant
                }, RoundedCornerShape(4.dp)
            )
            .padding(2.dp),
        text = char,
        style = CalorieTrackerTheme.typography.titleLarge,
        color = CalorieTrackerTheme.colors.onBackground,
        textAlign = TextAlign.Center
    )
}