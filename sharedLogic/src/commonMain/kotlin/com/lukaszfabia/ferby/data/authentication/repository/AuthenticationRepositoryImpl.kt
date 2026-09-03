package com.lukaszfabia.ferby.data.authentication.repository

import com.lukaszfabia.ferby.core.result.FerbyResult
import com.lukaszfabia.ferby.core.result.getOrDefault
import com.lukaszfabia.ferby.data.authentication.api.AuthenticationApi
import com.lukaszfabia.ferby.data.authentication.mapper.toDomain
import com.lukaszfabia.ferby.domain.authentication.model.AuthenticatedUser
import com.lukaszfabia.ferby.domain.authentication.model.GoogleCredential
import com.lukaszfabia.ferby.domain.authentication.model.Session
import com.lukaszfabia.ferby.domain.authentication.repository.AuthenticationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AuthenticationRepositoryImpl(
    private val api: AuthenticationApi,
) : AuthenticationRepository {
    private val _session =
        MutableStateFlow<Session?>(null)

    override val session = _session.asStateFlow()

    init {
        api.currentUser?.uid?.let { userId ->
            _session.value = Session(userId)
        }
    }

    override suspend fun authenticateWithGoogle(credential: GoogleCredential): FerbyResult<AuthenticatedUser> =
        api
            .authenticateWithGoogle(credential)
            .map { it.toDomain() }
            .also {
                it.getOrDefault()?.userId?.let {
                    _session.value = Session(it)
                }
            }

    override suspend fun signOut() {
        _session.value = null
        api.signOut()
    }
}
