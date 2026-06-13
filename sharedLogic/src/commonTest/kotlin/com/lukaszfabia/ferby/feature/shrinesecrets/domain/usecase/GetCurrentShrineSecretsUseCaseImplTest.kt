package com.lukaszfabia.ferby.feature.shrinesecrets.domain.usecase

import com.lukaszfabia.ferby.data.networking.model.ApiResult
import com.lukaszfabia.ferby.domain.model.Entity
import com.lukaszfabia.ferby.domain.model.Perk
import com.lukaszfabia.ferby.domain.type.Role
import com.lukaszfabia.ferby.feature.shrinesecrets.domain.model.ShrineSecrets
import com.lukaszfabia.ferby.feature.shrinesecrets.domain.repository.FakeShrineSecretsRepository
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDateTime
import kotlin.test.Test
import kotlin.test.assertEquals

class GetCurrentShrineSecretsUseCaseImplTest {

    private val repository = FakeShrineSecretsRepository()
    private val useCase = GetCurrentShrineSecretsUseCaseImpl(repository)

    @Test
    fun invoke_onSuccess_returnsSuccess() = runTest {
        // Given
        val expectedShrineSecrets = ShrineSecrets(
            perks = emptySet(),
            start = LocalDateTime(2023, 1, 1, 0, 0),
            end = LocalDateTime(2023, 1, 8, 0, 0),
            week = 1
        )
        val expectedResult = ApiResult.Success(expectedShrineSecrets)
        repository.result = expectedResult

        // When
        val result = useCase()

        // Then
        assertEquals(expectedResult, result)
    }

    @Test
    fun invoke_withPerks_returnsSuccess() = runTest {
        // Given
        val entity = Entity(Role.SURVIVOR, "Dwight", "Lead", "", emptySet())
        val perks = setOf(
            Perk("Bond", entity, "See others"),
            Perk("Prove Thyself", entity, "Work faster")
        )
        val shrineSecrets = ShrineSecrets(
            perks = perks,
            start = LocalDateTime(2023, 1, 1, 0, 0),
            end = LocalDateTime(2023, 1, 8, 0, 0),
            week = 1
        )
        val expectedResult = ApiResult.Success(shrineSecrets)
        repository.result = expectedResult

        // When
        val result = useCase()

        // Then
        assertEquals(expectedResult, result)
    }

    @Test
    fun invoke_onError_returnsError() = runTest {
        // Given
        val expectedResult = ApiResult.Error<ShrineSecrets>("Something went wrong")
        repository.result = expectedResult

        // When
        val result = useCase()

        // Then
        assertEquals(expectedResult, result)
    }
}
