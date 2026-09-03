package com.lukaszfabia.ferby.domain.authentication.usecase

import com.lukaszfabia.ferby.domain.authentication.model.Session
import com.lukaszfabia.ferby.domain.authentication.repository.AuthenticationRepository
import kotlinx.coroutines.flow.Flow

interface ObserveSessionUseCase {
    operator fun invoke(): Flow<Session?>
}

class ObserveSessionUseCaseImpl(
    private val repository: AuthenticationRepository,
) : ObserveSessionUseCase {
    override fun invoke(): Flow<Session?> = repository.session
}
