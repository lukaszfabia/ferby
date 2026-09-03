package com.lukaszfabia.ferby.domain.user.usecase

import com.lukaszfabia.ferby.core.result.FerbyResult
import com.lukaszfabia.ferby.domain.user.model.User
import com.lukaszfabia.ferby.domain.user.repository.UserRepository

interface SaveUserUseCase {
    suspend operator fun invoke(user: User): FerbyResult<User>
}

class SaveUserUseCaseImpl(
    private val repository: UserRepository,
) : SaveUserUseCase {
    override suspend fun invoke(user: User): FerbyResult<User> = repository.saveUser(user)
}
