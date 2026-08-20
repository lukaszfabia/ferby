package com.lukaszfabia.ferby.feature.shrinesecrets

import com.lukaszfabia.ferby.common.navigation.FakeNavigationDelegateImpl
import com.lukaszfabia.ferby.common.navigation.NavigationDelegate
import com.lukaszfabia.ferby.data.networking.model.ApiError
import com.lukaszfabia.ferby.data.networking.model.FerbyResult
import com.lukaszfabia.ferby.domain.model.Entity
import com.lukaszfabia.ferby.domain.model.Perk
import com.lukaszfabia.ferby.domain.type.Role
import com.lukaszfabia.ferby.feature.shrinesecrets.domain.model.ShrineSecrets
import com.lukaszfabia.ferby.feature.shrinesecrets.domain.usecase.GetCurrentShrineSecretsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.datetime.LocalDateTime
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class ShrineSecretsViewModelTest {
    private val testDispatcher = StandardTestDispatcher()

    private val navigationDelegate: NavigationDelegate = FakeNavigationDelegateImpl()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun init_onStart_setsStateToLoading() = runTest {
        // Given
        val useCase = FakeGetCurrentShrineSecretsUseCase(FerbyResult.Failure(ApiError.Unknown))

        // When
        val viewModel = ShrineSecretsViewModel(useCase, navigationDelegate)

        // Then
        assertEquals(ShrineSecretsState.Loading, viewModel.state.value)
    }

    @Test
    fun init_onSuccess_setsStateToSuccess() = runTest {
        // Given
        val shrineSecrets = ShrineSecrets(
            perks = setOf(
                Perk(
                    id = "1",
                    name = "Perk 1",
                    owner = Entity("1", Role.SURVIVOR, "Owner", "Desc", "img", emptySet()),
                    description = "Description",
                    image = "image"
                )
            ),
            start = LocalDateTime(2023, 1, 1, 0, 0),
            end = LocalDateTime(2023, 1, 8, 0, 0),
            week = 1
        )
        val useCase = FakeGetCurrentShrineSecretsUseCase(FerbyResult.Success(shrineSecrets))

        // When
        val viewModel = ShrineSecretsViewModel(useCase, navigationDelegate)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertEquals(ShrineSecretsState.Success(shrineSecrets), viewModel.state.value)
    }

    @Test
    fun init_onFailure_setsStateToFailure() = runTest {
        // Given
        val error = ApiError.Unknown
        val useCase = FakeGetCurrentShrineSecretsUseCase(FerbyResult.Failure(error))

        // When
        val viewModel = ShrineSecretsViewModel(useCase, navigationDelegate)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertEquals(ShrineSecretsState.Failure(error), viewModel.state.value)
    }
}

private class FakeGetCurrentShrineSecretsUseCase(
    private val result: FerbyResult<ShrineSecrets>
) : GetCurrentShrineSecretsUseCase {
    override suspend fun invoke(): FerbyResult<ShrineSecrets> = result
}
