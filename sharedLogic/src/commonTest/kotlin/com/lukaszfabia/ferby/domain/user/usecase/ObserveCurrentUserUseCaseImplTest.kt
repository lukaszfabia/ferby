package com.lukaszfabia.ferby.domain.user.usecase

import com.lukaszfabia.ferby.domain.authentication.model.Session
import com.lukaszfabia.ferby.domain.authentication.repository.FakeAuthenticationRepository
import com.lukaszfabia.ferby.domain.authentication.usecase.ObserveSessionUseCaseImpl
import com.lukaszfabia.ferby.domain.user.model.User
import com.lukaszfabia.ferby.domain.user.repository.FakeUserRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Clock

class ObserveCurrentUserUseCaseImplTest {
    private val authRepository = FakeAuthenticationRepository()
    private val userRepository = FakeUserRepository()
    private val useCase =
        ObserveCurrentUserUseCaseImpl(
            observeSessionUseCase = ObserveSessionUseCaseImpl(authRepository),
            observeUserUseCase = ObserveUserUseCaseImpl(userRepository),
        )

    @Test
    fun invoke_sessionExists_returnsCurrentUser() =
        runTest {
            // Given
            val userId = "id"
            val user =
                User(
                    id = userId,
                    email = "email",
                    name = "name",
                    providerId = "provider",
                    photoUrl = "photo",
                    username = "username",
                    createdAt = Clock.System.now(),
                )
            userRepository.saveUser(user)
            authRepository.emitSession(Session(userId))

            // When
            val result = useCase().first()

            // Then
            assertEquals(user, result)
        }

    @Test
    fun invoke_sessionIsNull_returnsNull() =
        runTest {
            // Given
            authRepository.emitSession(null)

            // When
            val result = useCase().first()

            // Then
            assertEquals(null, result)
        }
}
