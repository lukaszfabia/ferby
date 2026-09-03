package com.lukaszfabia.ferby.domain.user.usecase

import com.lukaszfabia.ferby.domain.user.model.User
import com.lukaszfabia.ferby.domain.user.repository.FakeUserRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Clock

class ObserveUserUseCaseImplTest {
    private val repository = FakeUserRepository()
    private val useCase = ObserveUserUseCaseImpl(repository)

    @Test
    fun invoke_userExists_returnsFlowOfUser() =
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
            val result = useCase(user.id).first()

            // Then
            assertEquals(user, result)
        }
}
