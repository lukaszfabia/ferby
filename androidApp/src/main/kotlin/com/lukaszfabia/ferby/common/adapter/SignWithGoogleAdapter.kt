package com.lukaszfabia.ferby.common.adapter

import android.app.Activity
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialCancellationException
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.lukaszfabia.ferby.R
import com.lukaszfabia.ferby.core.result.FerbyResult
import com.lukaszfabia.ferby.core.result.GoogleSsoError
import com.lukaszfabia.ferby.domain.authentication.model.GoogleCredential

interface SignWithGoogleAdapter {
    suspend fun signIn(activity: Activity): FerbyResult<GoogleCredential>
}

class SignWithGoogleAdapterImpl : SignWithGoogleAdapter {

    override suspend fun signIn(activity: Activity): FerbyResult<GoogleCredential> =
        try {
            val credentialManager = CredentialManager.create(activity)
            val googleIdOption = GetSignInWithGoogleOption.Builder(
                serverClientId = activity.getString(R.string.default_web_client_id)
            ).build()

            val request = GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build()

            val result = credentialManager.getCredential(
                request = request,
                context = activity,
            )

            result.toGoogleCredential()

        }
        catch (e: GetCredentialCancellationException) {
            Log.e(
                "SignWithGoogleAdapter",
                "Google sign-in cancelled: ${e.errorMessage}",
                e
            )

            FerbyResult.Failure(GoogleSsoError.Cancelled)
        } catch (e: Exception) {
            e.printStackTrace()

            FerbyResult.Failure(GoogleSsoError.SignInFailed)
        }

    private fun GetCredentialResponse.toGoogleCredential(): FerbyResult<GoogleCredential> {
        val credential = this.credential
        if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
            val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
            print(googleIdTokenCredential)
            return FerbyResult.Success(
                GoogleCredential(
                    idToken = googleIdTokenCredential.idToken,
                    accessToken = null
                )
            )
        }
        return FerbyResult.Failure(GoogleSsoError.MissingToken)
    }
}
