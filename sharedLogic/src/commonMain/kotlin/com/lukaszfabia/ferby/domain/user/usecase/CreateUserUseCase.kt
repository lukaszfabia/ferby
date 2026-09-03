package com.lukaszfabia.ferby.domain.user.usecase

import com.lukaszfabia.ferby.core.result.FerbyResult
import com.lukaszfabia.ferby.domain.authentication.model.AuthenticatedUser
import com.lukaszfabia.ferby.domain.user.model.User
import com.lukaszfabia.ferby.domain.user.repository.UserRepository

interface CreateUserUseCase {
    suspend operator fun invoke(authenticatedUser: AuthenticatedUser): FerbyResult<User>
}

class CreateUserUseCaseImpl(
    private val repository: UserRepository,
) : CreateUserUseCase {
    override suspend fun invoke(authenticatedUser: AuthenticatedUser): FerbyResult<User> = repository.createUser(authenticatedUser)
}
