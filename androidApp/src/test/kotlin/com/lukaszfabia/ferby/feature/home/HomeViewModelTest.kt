package com.lukaszfabia.ferby.feature.home

import com.lukaszfabia.ferby.domain.authentication.usecase.SignOutUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {
    private val testDispatcher = StandardTestDispatcher()

    private val signOutUseCase = FakeSignOutUseCase()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun handleEvent_onSignOutClick_callsSignOutUseCase() = runTest {
        // Given
        val viewModel = HomeViewModel(signOutUseCase)

        // When
        viewModel.handleEvent(HomeEvent.OnSignOutClick)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertEquals(1, signOutUseCase.calledCount)
    }
}

private class FakeSignOutUseCase : SignOutUseCase {
    var calledCount = 0
    override suspend fun invoke() {
        calledCount++
    }
}
