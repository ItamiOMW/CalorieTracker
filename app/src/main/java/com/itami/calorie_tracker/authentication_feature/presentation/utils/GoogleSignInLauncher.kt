package com.itami.calorie_tracker.authentication_feature.presentation.utils

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import com.itami.calorie_tracker.R

@Composable
fun OneTapSignInWithGoogle(
    opened:  Boolean,
    clientId: String,
    rememberAccount: Boolean = true,
    nonce: String? = null,
    onIdTokenReceived: (String) -> Unit,
    onDialogDismissed: (String?) -> Unit,
) {
    val activity = LocalContext.current as Activity
    val activityLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        try {
            if (result.resultCode == Activity.RESULT_OK) {
                val oneTapClient = Identity.getSignInClient(activity)
                val credentials = oneTapClient.getSignInCredentialFromIntent(result.data)
                val tokenId = credentials.googleIdToken
                if (tokenId != null) {
                    onIdTokenReceived(tokenId)
                }
            } else {
                onDialogDismissed(activity.getString(R.string.error_something_went_wrong))
            }
        } catch (e: ApiException) {
            when (e.statusCode) {
                CommonStatusCodes.CANCELED -> {
                    onDialogDismissed(null)
                }
                CommonStatusCodes.NETWORK_ERROR -> {
                    onDialogDismissed(activity.getString(R.string.error_network))
                }

                else -> {
                    e.printStackTrace()
                    onDialogDismissed(activity.getString(R.string.error_something_went_wrong))
                }
            }
        }
    }

    LaunchedEffect(key1 = opened) {
        if (opened) {
            signIn(
                activity = activity,
                clientId = clientId,
                rememberAccount = rememberAccount,
                nonce = nonce,
                launchActivityResult = { intentSenderRequest ->
                    activityLauncher.launch(intentSenderRequest)
                },
                onError = {
                    onDialogDismissed(it)
                }
            )
        }
    }
}

private fun signIn(
    activity: Activity,
    clientId: String,
    rememberAccount: Boolean,
    nonce: String?,
    launchActivityResult: (IntentSenderRequest) -> Unit,
    onError: (String?) -> Unit,
) {
    val oneTapClient = Identity.getSignInClient(activity)
    val signInRequest = BeginSignInRequest.builder()
        .setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                .setNonce(nonce)
                .setServerClientId(clientId)
                .setFilterByAuthorizedAccounts(rememberAccount)
                .build()
        )
        .setAutoSelectEnabled(true)
        .build()

    oneTapClient.beginSignIn(signInRequest)
        .addOnSuccessListener { result ->
            try {
                launchActivityResult(
                    IntentSenderRequest.Builder(
                        result.pendingIntent.intentSender
                    ).build()
                )
            } catch (e: Exception) {
                e.printStackTrace()
                onError(activity.getString(R.string.error_something_went_wrong))
            }
        }
        .addOnFailureListener {
            signUp(
                activity = activity,
                clientId = clientId,
                nonce = nonce,
                launchActivityResult = launchActivityResult,
                onError = onError
            )
        }
}

private fun signUp(
    activity: Activity,
    clientId: String,
    nonce: String?,
    launchActivityResult: (IntentSenderRequest) -> Unit,
    onError: (String?) -> Unit,
) {
    val oneTapClient = Identity.getSignInClient(activity)
    val signInRequest = BeginSignInRequest.builder()
        .setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                .setNonce(nonce)
                .setServerClientId(clientId)
                .setFilterByAuthorizedAccounts(false)
                .build()
        )
        .build()

    oneTapClient.beginSignIn(signInRequest)
        .addOnSuccessListener { result ->
            try {
                launchActivityResult(
                    IntentSenderRequest.Builder(
                        result.pendingIntent.intentSender
                    ).build()
                )
            } catch (e: Exception) {
                e.printStackTrace()
                onError(activity.getString(R.string.error_something_went_wrong))
            }
        }
        .addOnFailureListener {
            it.printStackTrace()
            onError(activity.getString(R.string.error_something_went_wrong))
        }
}