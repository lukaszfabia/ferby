package com.lukaszfabia.ferby.domain.authentication.repository

import com.lukaszfabia.ferby.core.result.FerbyResult
import com.lukaszfabia.ferby.domain.authentication.model.AuthenticatedUser
import com.lukaszfabia.ferby.domain.authentication.model.GoogleCredential
import com.lukaszfabia.ferby.domain.authentication.model.Session
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FakeAuthenticationRepository : AuthenticationRepository {
    private val _session = MutableStateFlow<Session?>(null)
    override val session: StateFlow<Session?> = _session.asStateFlow()

    var authenticateResult: FerbyResult<AuthenticatedUser>? = null
    var signOutCalledCount = 0

    fun emitSession(session: Session?) {
        _session.value = session
    }

    override suspend fun authenticateWithGoogle(credential: GoogleCredential): FerbyResult<AuthenticatedUser> {
        val result = authenticateResult ?: throw IllegalStateException("Provide authenticateResult")
        if (result is FerbyResult.Success) {
            _session.value = Session(result.data.userId)
        }
        return result
    }

    override suspend fun signOut() {
        signOutCalledCount++
        _session.value = null
    }
}
