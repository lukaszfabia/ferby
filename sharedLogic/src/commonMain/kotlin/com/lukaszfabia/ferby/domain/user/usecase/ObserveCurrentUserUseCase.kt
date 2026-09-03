package com.lukaszfabia.ferby.domain.user.usecase

import com.lukaszfabia.ferby.domain.authentication.usecase.ObserveSessionUseCase
import com.lukaszfabia.ferby.domain.user.model.User
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf

interface ObserveCurrentUserUseCase {
    operator fun invoke(): Flow<User?>
}

class ObserveCurrentUserUseCaseImpl(
    private val observeSessionUseCase: ObserveSessionUseCase,
    private val observeUserUseCase: ObserveUserUseCase,
) : ObserveCurrentUserUseCase {
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun invoke(): Flow<User?> =
        observeSessionUseCase()
            .flatMapLatest { session ->
                session?.let {
                    observeUserUseCase(userId = it.userId)
                } ?: flowOf(null)
            }
}
