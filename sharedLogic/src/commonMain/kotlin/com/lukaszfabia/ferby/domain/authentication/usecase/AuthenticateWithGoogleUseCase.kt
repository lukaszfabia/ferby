package com.lukaszfabia.ferby.domain.authentication.usecase

import com.lukaszfabia.ferby.core.result.FerbyResult
import com.lukaszfabia.ferby.domain.authentication.model.AuthenticatedUser
import com.lukaszfabia.ferby.domain.authentication.model.GoogleCredential
import com.lukaszfabia.ferby.domain.authentication.repository.AuthenticationRepository

interface AuthenticateWithGoogleUseCase {
    suspend operator fun invoke(credential: GoogleCredential): FerbyResult<AuthenticatedUser>
}

class AuthenticateWithGoogleUseCaseImpl(
    private val repository: AuthenticationRepository,
) : AuthenticateWithGoogleUseCase {
    override suspend fun invoke(credential: GoogleCredential): FerbyResult<AuthenticatedUser> =
        repository.authenticateWithGoogle(credential)
}
