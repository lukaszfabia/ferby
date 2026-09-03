package com.lukaszfabia.ferby.feature.shrinesecrets

import com.lukaszfabia.ferby.common.navigation.FakeNavigationDelegateImpl
import com.lukaszfabia.ferby.common.navigation.NavigationDelegate
import com.lukaszfabia.ferby.core.cache.MemoryStoreImpl
import com.lukaszfabia.ferby.core.result.FerbyError
import com.lukaszfabia.ferby.core.result.FerbyResult
import com.lukaszfabia.ferby.domain.deadbydaylight.model.Entity
import com.lukaszfabia.ferby.domain.deadbydaylight.model.Perk
import com.lukaszfabia.ferby.domain.deadbydaylight.type.Role
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

    private val memoryStore = MemoryStoreImpl<Perk>()

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
        val useCase = FakeGetCurrentShrineSecretsUseCase(FerbyResult.Failure(FerbyError.Unknown))

        // When
        val viewModel = ShrineSecretsViewModel(useCase, memoryStore, navigationDelegate)

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
        val viewModel = ShrineSecretsViewModel(useCase, memoryStore, navigationDelegate)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertEquals(ShrineSecretsState.Success(shrineSecrets), viewModel.state.value)
    }

    @Test
    fun init_onFailure_setsStateToFailure() = runTest {
        // Given
        val error = FerbyError.Unknown
        val useCase = FakeGetCurrentShrineSecretsUseCase(FerbyResult.Failure(error))

        // When
        val viewModel = ShrineSecretsViewModel(useCase, memoryStore, navigationDelegate)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertEquals(ShrineSecretsState.Failure(error), viewModel.state.value)
    }

    @Test
    fun handleEvent_onPerkClick_savesPerkInMemoryStoreAndNavigates() = runTest {
        // Given
        val perk = Perk(
            id = "1",
            name = "Perk 1",
            owner = Entity("1", Role.SURVIVOR, "Owner", "Desc", "img", emptySet()),
            description = "Description",
            image = "image"
        )
        val useCase = FakeGetCurrentShrineSecretsUseCase(FerbyResult.Failure(FerbyError.Unknown))
        val viewModel = ShrineSecretsViewModel(useCase, memoryStore, navigationDelegate)

        // When
        viewModel.handleEvent(ShrineSecretsEvent.OnPerkClick(perk))
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertEquals(perk, memoryStore.get())
        assertEquals(
            ShrineSecretsDetailRoute,
            (navigationDelegate as FakeNavigationDelegateImpl).navigatedRoutes.last()
        )
    }
}

private class FakeGetCurrentShrineSecretsUseCase(
    private val result: FerbyResult<ShrineSecrets>
) : GetCurrentShrineSecretsUseCase {
    override suspend fun invoke(): FerbyResult<ShrineSecrets> = result
}
