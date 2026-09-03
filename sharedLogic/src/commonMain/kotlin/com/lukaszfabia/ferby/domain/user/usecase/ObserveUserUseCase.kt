package com.lukaszfabia.ferby.domain.user.usecase

import com.lukaszfabia.ferby.domain.user.model.User
import com.lukaszfabia.ferby.domain.user.repository.UserRepository
import kotlinx.coroutines.flow.Flow

interface ObserveUserUseCase {
    operator fun invoke(userId: String): Flow<User?>
}

class ObserveUserUseCaseImpl(
    private val repository: UserRepository,
) : ObserveUserUseCase {
    override fun invoke(userId: String): Flow<User?> = repository.observeUser(userId)
}
