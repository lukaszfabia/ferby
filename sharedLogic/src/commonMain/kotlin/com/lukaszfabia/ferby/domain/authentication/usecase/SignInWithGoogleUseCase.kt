package com.lukaszfabia.ferby.domain.authentication.usecase

import com.lukaszfabia.ferby.core.result.FerbyResult
import com.lukaszfabia.ferby.core.result.flatMap
import com.lukaszfabia.ferby.core.result.ifNull
import com.lukaszfabia.ferby.domain.authentication.model.GoogleCredential
import com.lukaszfabia.ferby.domain.user.model.User
import com.lukaszfabia.ferby.domain.user.usecase.CreateUserUseCase
import com.lukaszfabia.ferby.domain.user.usecase.GetUserUseCase

interface SignInWithGoogleUseCase {
    suspend operator fun invoke(credential: GoogleCredential): FerbyResult<User>
}

class SignInWithGoogleUseCaseImpl(
    private val authenticateWithGoogleUseCase: AuthenticateWithGoogleUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val createUserUseCase: CreateUserUseCase,
) : SignInWithGoogleUseCase {
    override suspend fun invoke(credential: GoogleCredential): FerbyResult<User> =
        authenticateWithGoogleUseCase(credential)
            .flatMap {
                getUserUseCase(it.userId)
                    .ifNull {
                        createUserUseCase(it)
                    }
            }
}
