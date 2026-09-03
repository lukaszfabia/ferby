package com.lukaszfabia.ferby.data.authentication.api

import com.lukaszfabia.ferby.core.result.FerbyResult
import com.lukaszfabia.ferby.domain.authentication.model.GoogleCredential
import dev.gitlive.firebase.auth.FirebaseUser

interface AuthenticationApi {
    val currentUser: FirebaseUser?

    suspend fun authenticateWithGoogle(credential: GoogleCredential): FerbyResult<FirebaseUser>

    suspend fun signOut()
}
