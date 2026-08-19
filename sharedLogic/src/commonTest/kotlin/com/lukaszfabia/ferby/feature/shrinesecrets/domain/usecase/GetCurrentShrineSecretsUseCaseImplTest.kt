package com.lukaszfabia.ferby.feature.shrinesecrets.domain.usecase

import com.lukaszfabia.ferby.data.networking.model.ApiError
import com.lukaszfabia.ferby.data.networking.model.FerbyResult
import com.lukaszfabia.ferby.domain.model.Entity
import com.lukaszfabia.ferby.domain.model.Perk
import com.lukaszfabia.ferby.domain.type.Role
import com.lukaszfabia.ferby.feature.shrinesecrets.domain.model.ShrineSecrets
import com.lukaszfabia.ferby.feature.shrinesecrets.domain.repository.FakeShrineSecretsRepository
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDateTime
import kotlin.collections.emptySet
import kotlin.test.Test
import kotlin.test.assertEquals

class GetCurrentShrineSecretsUseCaseImplTest {
    private val repository = FakeShrineSecretsRepository()
    private val useCase = GetCurrentShrineSecretsUseCaseImpl(repository)

    @Test
    fun invoke_onSuccess_returnsSuccess() =
        runTest {
            // Given
            val expectedShrineSecrets =
                ShrineSecrets(
                    perks = emptySet(),
                    start = LocalDateTime(2023, 1, 1, 0, 0),
                    end = LocalDateTime(2023, 1, 8, 0, 0),
                    week = 1,
                )
            val expectedResult = FerbyResult.Success(expectedShrineSecrets)
            repository.result = expectedResult

            // When
            val result = useCase()

            // Then
            assertEquals(expectedResult, result)
        }

    @Test
    fun invoke_withPerks_returnsSuccess() =
        runTest {
            // Given
            val entity = Entity(id = "11", Role.SURVIVOR, "Dwight", "Lead", "foo", emptySet())
            val perks =
                setOf(
                    Perk(id = "12", "Bond", entity, "See others", image = "foo"),
                    Perk(id = "13", "Prove Thyself", entity, "Work faster", image = "foo"),
                )
            val shrineSecrets =
                ShrineSecrets(
                    perks = perks,
                    start = LocalDateTime(2023, 1, 1, 0, 0),
                    end = LocalDateTime(2023, 1, 8, 0, 0),
                    week = 1,
                )
            val expectedResult = FerbyResult.Success(shrineSecrets)
            repository.result = expectedResult

            // When
            val result = useCase()

            // Then
            assertEquals(expectedResult, result)
        }

    @Test
    fun invoke_onError_returnsError() =
        runTest {
            // Given
            val expectedResult = FerbyResult.Failure(ApiError.NotFound)
            repository.result = expectedResult

            // When
            val result = useCase()

            // Then
            assertEquals(expectedResult, result)
        }
}
