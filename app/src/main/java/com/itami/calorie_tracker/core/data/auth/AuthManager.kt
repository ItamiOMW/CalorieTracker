package com.itami.calorie_tracker.core.data.auth

interface AuthManager {

    val token: String?

    fun setToken(token: String?)

}