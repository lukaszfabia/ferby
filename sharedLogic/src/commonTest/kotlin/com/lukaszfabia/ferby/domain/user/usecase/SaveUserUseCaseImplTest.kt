package com.lukaszfabia.ferby.domain.user.usecase

import com.lukaszfabia.ferby.core.result.FerbyResult
import com.lukaszfabia.ferby.domain.user.model.User
import com.lukaszfabia.ferby.domain.user.repository.FakeUserRepository
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.time.Clock

class SaveUserUseCaseImplTest {
    private val repository = FakeUserRepository()
    private val useCase = SaveUserUseCaseImpl(repository)

    @Test
    fun invoke_onSuccess_returnsSuccess() =
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

            // When
            val result = useCase(user)

            // Then
            assertTrue(result is FerbyResult.Success)
            assertEquals(user, result.data)

            val savedUserResult = repository.getUser(user.id)
            assertTrue(savedUserResult is FerbyResult.Success)
            assertEquals(user, savedUserResult.data)
        }
}
