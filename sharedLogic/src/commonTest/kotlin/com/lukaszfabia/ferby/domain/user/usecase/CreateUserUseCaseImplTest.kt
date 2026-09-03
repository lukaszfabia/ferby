package com.lukaszfabia.ferby.domain.user.usecase

import com.lukaszfabia.ferby.core.result.FerbyResult
import com.lukaszfabia.ferby.domain.authentication.model.AuthenticatedUser
import com.lukaszfabia.ferby.domain.user.repository.FakeUserRepository
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CreateUserUseCaseImplTest {
    private val repository = FakeUserRepository()
    private val useCase = CreateUserUseCaseImpl(repository)

    @Test
    fun invoke_onSuccess_returnsSuccess() =
        runTest {
            // Given
            val authenticatedUser =
                AuthenticatedUser(
                    userId = "id",
                    email = "email",
                    displayName = "name",
                    photoUrl = "photo",
                    providerId = "provider",
                )

            // When
            val result = useCase(authenticatedUser)

            // Then
            assertTrue(result is FerbyResult.Success)
            val user = result.data
            assertEquals(authenticatedUser.userId, user.id)
            assertEquals(authenticatedUser.email, user.email)
            assertEquals(authenticatedUser.displayName, user.name)

            val savedUserResult = repository.getUser(authenticatedUser.userId)
            assertTrue(savedUserResult is FerbyResult.Success)
            assertEquals(user, savedUserResult.data)
        }
}
