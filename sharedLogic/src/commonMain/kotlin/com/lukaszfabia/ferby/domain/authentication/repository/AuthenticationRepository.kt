package com.lukaszfabia.ferby.domain.authentication.repository

import com.lukaszfabia.ferby.core.result.FerbyResult
import com.lukaszfabia.ferby.domain.authentication.model.AuthenticatedUser
import com.lukaszfabia.ferby.domain.authentication.model.GoogleCredential
import com.lukaszfabia.ferby.domain.authentication.model.Session
import kotlinx.coroutines.flow.StateFlow

interface AuthenticationRepository {
    val session: StateFlow<Session?>

    suspend fun authenticateWithGoogle(credential: GoogleCredential): FerbyResult<AuthenticatedUser>

    suspend fun signOut()
}
