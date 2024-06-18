package com.itami.calorie_tracker.core.domain.utils

import android.util.Patterns
import com.itami.calorie_tracker.core.domain.exceptions.AppException
import com.itami.calorie_tracker.core.domain.exceptions.FeedbackMessageException
import com.itami.calorie_tracker.core.domain.exceptions.UserCredentialsException
import com.itami.calorie_tracker.core.domain.exceptions.UserInfoException
import com.itami.calorie_tracker.core.utils.Constants

object ValidationUtil {

    fun validateFeedbackMessage(message: String): AppException? {
        val trimmedMessage = message.trim()
        if (trimmedMessage.isEmpty()) {
            return FeedbackMessageException.EmptyMessageException
        }

        if (trimmedMessage.length < 20) {
            return FeedbackMessageException.ShortMessageException
        }

        if (trimmedMessage.length > 1000) {
            return FeedbackMessageException.LargeMessageException
        }

        return null
    }

    fun validateResetCode(code: Int): AppException? {
        if (code < 100000 || code > 999999) {
            return UserCredentialsException.InvalidResetCodeException
        }

        return null
    }

    fun validateName(name: String): UserCredentialsException? {
        val trimmedName = name.trim()

        if (trimmedName.isBlank()) {
            return UserCredentialsException.EmptyNameFieldException
        }

        return null
    }

    fun validateEmail(email: String): UserCredentialsException? {
        val trimmedEmail = email.trim()

        if (trimmedEmail.isBlank()) {
            return UserCredentialsException.EmptyEmailFieldException
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(trimmedEmail).matches()) {
            return UserCredentialsException.InvalidEmailException
        }

        return null
    }

    fun validatePassword(password: String): AppException? {
        val trimmedPassword = password.trim()

        if (trimmedPassword.isBlank()) {
            return UserCredentialsException.EmptyPasswordFieldException
        }

        if (trimmedPassword.length < Constants.MIN_PASSWORD_LENGTH) {
            return UserCredentialsException.ShortPasswordException
        }

        return null
    }

    fun validatePasswords(
        password: String,
        repeatPassword: String
    ): AppException? {
        val trimmedPassword = password.trim()
        val trimmedRepeatPassword = repeatPassword.trim()

        if (trimmedRepeatPassword.isBlank()) {
            return UserCredentialsException.EmptyRepeatPasswordFieldException
        }

        if (trimmedPassword != trimmedRepeatPassword) {
            return UserCredentialsException.PasswordsDoNotMatchException
        }

        return null
    }

    fun validateHeight(heightCm: Int): UserInfoException? {
        if (heightCm > Constants.MAX_HEIGHT_CM || heightCm < Constants.MIN_HEIGHT_CM) {
            return UserInfoException.InvalidHeightException
        }

        return null
    }

    fun validateWeight(weightGrams: Int): UserInfoException? {
        if (weightGrams > Constants.MAX_WEIGHT_GRAMS || weightGrams < Constants.MIN_WEIGHT_GRAMS) {
            return UserInfoException.InvalidWeightException
        }

        return null
    }

    fun validateAge(age: Int): UserInfoException? {
        if (age > Constants.MAX_AGE || age < Constants.MIN_AGE) {
            return UserInfoException.InvalidAgeException
        }

        return null
    }

}