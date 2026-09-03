package com.lukaszfabia.ferby.domain.user.usecase

import com.lukaszfabia.ferby.core.result.FerbyResult
import com.lukaszfabia.ferby.domain.user.model.User
import com.lukaszfabia.ferby.domain.user.repository.UserRepository

interface GetUserUseCase {
    suspend operator fun invoke(userId: String): FerbyResult<User?>
}

class GetUserUseCaseImpl(
    private val repository: UserRepository,
) : GetUserUseCase {
    override suspend fun invoke(userId: String): FerbyResult<User?> = repository.getUser(userId)
}
