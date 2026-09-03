package com.lukaszfabia.ferby.domain.authentication.usecase

import com.lukaszfabia.ferby.core.result.FerbyResult
import com.lukaszfabia.ferby.core.result.GoogleSsoError
import com.lukaszfabia.ferby.domain.authentication.model.AuthenticatedUser
import com.lukaszfabia.ferby.domain.authentication.model.GoogleCredential
import com.lukaszfabia.ferby.domain.authentication.repository.FakeAuthenticationRepository
import com.lukaszfabia.ferby.domain.user.model.User
import com.lukaszfabia.ferby.domain.user.repository.FakeUserRepository
import com.lukaszfabia.ferby.domain.user.usecase.CreateUserUseCaseImpl
import com.lukaszfabia.ferby.domain.user.usecase.GetUserUseCaseImpl
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.time.Clock

class SignInWithGoogleUseCaseImplTest {
    private val authRepository = FakeAuthenticationRepository()
    private val userRepository = FakeUserRepository()

    private val authenticateWithGoogleUseCase = AuthenticateWithGoogleUseCaseImpl(authRepository)
    private val getUserUseCase = GetUserUseCaseImpl(userRepository)
    private val createUserUseCase = CreateUserUseCaseImpl(userRepository)

    private val useCase =
        SignInWithGoogleUseCaseImpl(
            authenticateWithGoogleUseCase = authenticateWithGoogleUseCase,
            getUserUseCase = getUserUseCase,
            createUserUseCase = createUserUseCase,
        )

    @Test
    fun invoke_userExists_returnsExistingUser() =
        runTest {
            // Given
            val credential = GoogleCredential("token", null)
            val userId = "user_id"
            val authenticatedUser =
                AuthenticatedUser(
                    userId = userId,
                    email = "test@example.com",
                    displayName = "Test User",
                    photoUrl = null,
                    providerId = "google.com",
                )
            val existingUser =
                User(
                    id = userId,
                    email = "test@example.com",
                    name = "Test User",
                    providerId = "google.com",
                    photoUrl = null,
                    username = "existing",
                    createdAt = Clock.System.now(),
                )

            authRepository.authenticateResult = FerbyResult.Success(authenticatedUser)
            userRepository.saveUser(existingUser)

            // When
            val result = useCase(credential)

            // Then
            assertTrue(result is FerbyResult.Success)
            assertEquals(existingUser, result.data)
        }

    @Test
    fun invoke_userDoesNotExist_createsAndReturnsNewUser() =
        runTest {
            // Given
            val credential = GoogleCredential("token", null)
            val userId = "new_user_id"
            val authenticatedUser =
                AuthenticatedUser(
                    userId = userId,
                    email = "new@example.com",
                    displayName = "New User",
                    photoUrl = "photo",
                    providerId = "google.com",
                )

            authRepository.authenticateResult = FerbyResult.Success(authenticatedUser)
            // userRepository is empty

            // When
            val result = useCase(credential)

            // Then
            assertTrue(result is FerbyResult.Success)
            val createdUser = result.data
            assertEquals(userId, createdUser.id)
            assertEquals(authenticatedUser.email, createdUser.email)

            val savedUserResult = userRepository.getUser(userId)
            assertTrue(savedUserResult is FerbyResult.Success)
            assertEquals(createdUser, savedUserResult.data)
        }

    @Test
    fun invoke_authenticationFails_returnsError() =
        runTest {
            // Given
            val credential = GoogleCredential("token", null)
            val expectedError = FerbyResult.Failure(GoogleSsoError.SignInFailed)
            authRepository.authenticateResult = expectedError

            // When
            val result = useCase(credential)

            // Then
            assertEquals(expectedError, result)
        }
}
