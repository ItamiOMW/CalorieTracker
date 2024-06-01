package com.itami.calorie_tracker.core.domain.exceptions

sealed class UserCredentialsException: AppException() {

    data object EmptyNameFieldException : UserCredentialsException()

    data object EmptyEmailFieldException : UserCredentialsException()

    data object EmptyPasswordFieldException : UserCredentialsException()

    data object EmptyRepeatPasswordFieldException : UserCredentialsException()

    data object ShortPasswordException : UserCredentialsException()

    data object PasswordsDoNotMatchException: UserCredentialsException()

    data object InvalidEmailException : UserCredentialsException()

    data object InvalidResetCodeException : UserCredentialsException()

}