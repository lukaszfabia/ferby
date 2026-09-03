package com.lukaszfabia.ferby.domain.authentication.usecase

import com.lukaszfabia.ferby.domain.authentication.repository.FakeAuthenticationRepository
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class SignOutUseCaseImplTest {
    private val repository = FakeAuthenticationRepository()
    private val useCase = SignOutUseCaseImpl(repository)

    @Test
    fun invoke_callsRepositorySignOut() =
        runTest {
            // When
            useCase()

            // Then
            assertEquals(1, repository.signOutCalledCount)
        }
}
