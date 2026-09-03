package com.lukaszfabia.ferby.domain.authentication.usecase

import com.lukaszfabia.ferby.domain.authentication.repository.AuthenticationRepository

interface SignOutUseCase {
    suspend operator fun invoke()
}

class SignOutUseCaseImpl(
    private val repository: AuthenticationRepository,
) : SignOutUseCase {
    override suspend fun invoke() = repository.signOut()
}
