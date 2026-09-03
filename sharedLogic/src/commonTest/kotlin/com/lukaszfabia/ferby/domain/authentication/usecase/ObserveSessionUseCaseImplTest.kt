package com.lukaszfabia.ferby.domain.authentication.usecase

import com.lukaszfabia.ferby.domain.authentication.model.Session
import com.lukaszfabia.ferby.domain.authentication.repository.FakeAuthenticationRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class ObserveSessionUseCaseImplTest {
    private val repository = FakeAuthenticationRepository()
    private val useCase = ObserveSessionUseCaseImpl(repository)

    @Test
    fun invoke_returnsSessionFromRepository() =
        runTest {
            // Given
            val expectedSession = Session("userId")
            repository.emitSession(expectedSession)

            // When
            val result = useCase().first()

            // Then
            assertEquals(expectedSession, result)
        }

    @Test
    fun invoke_whenSessionIsNull_returnsNull() =
        runTest {
            // Given
            repository.emitSession(null)

            // When
            val result = useCase().first()

            // Then
            assertEquals(null, result)
        }
}
