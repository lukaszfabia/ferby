package com.lukaszfabia.ferby.domain.user.usecase

import com.lukaszfabia.ferby.core.result.FerbyResult
import com.lukaszfabia.ferby.domain.user.model.User
import com.lukaszfabia.ferby.domain.user.repository.FakeUserRepository
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.time.Clock

class GetUserUseCaseImplTest {
    private val repository = FakeUserRepository()
    private val useCase = GetUserUseCaseImpl(repository)

    @Test
    fun invoke_userExists_returnsUser() =
        runTest {
            // Given
            val user =
                User(
                    id = "id",
                    email = "email",
                    name = "name",
                    providerId = "provider",
                    photoUrl = "photo",
                    username = "username",
                    createdAt = Clock.System.now(),
                )
            repository.saveUser(user)

            // When
            val result = useCase(user.id)

            // Then
            assertTrue(result is FerbyResult.Success)
            assertEquals(user, result.data)
        }

    @Test
    fun invoke_userDoesNotExist_returnsNull() =
        runTest {
            // When
            val result = useCase("non_existent")

            // Then
            assertTrue(result is FerbyResult.Success)
            assertEquals(null, result.data)
        }
}
