package com.lukaszfabia.ferby.domain.authentication.usecase

import com.lukaszfabia.ferby.core.result.FerbyResult
import com.lukaszfabia.ferby.core.result.GoogleSsoError
import com.lukaszfabia.ferby.domain.authentication.model.AuthenticatedUser
import com.lukaszfabia.ferby.domain.authentication.model.GoogleCredential
import com.lukaszfabia.ferby.domain.authentication.repository.FakeAuthenticationRepository
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class AuthenticateWithGoogleUseCaseImplTest {
    private val repository = FakeAuthenticationRepository()
    private val useCase = AuthenticateWithGoogleUseCaseImpl(repository)

    @Test
    fun invoke_onSuccess_returnsSuccess() =
        runTest {
            // Given
            val credential = GoogleCredential("token", null)
            val authenticatedUser =
                AuthenticatedUser(
                    userId = "id",
                    email = "email",
                    displayName = "name",
                    photoUrl = "photo",
                    providerId = "provider",
                )
            val expectedResult = FerbyResult.Success(authenticatedUser)
            repository.authenticateResult = expectedResult

            // When
            val result = useCase(credential)

            // Then
            assertEquals(expectedResult, result)
        }

    @Test
    fun invoke_onError_returnsError() =
        runTest {
            // Given
            val credential = GoogleCredential("token", null)
            val expectedResult = FerbyResult.Failure(GoogleSsoError.SignInFailed)
            repository.authenticateResult = expectedResult

            // When
            val result = useCase(credential)

            // Then
            assertEquals(expectedResult, result)
        }
}
