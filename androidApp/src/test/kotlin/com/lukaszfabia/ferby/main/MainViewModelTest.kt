package com.lukaszfabia.ferby.main

import com.lukaszfabia.ferby.common.navigation.FakeNavigationDelegateImpl
import com.lukaszfabia.ferby.common.navigation.TabRoute
import com.lukaszfabia.ferby.domain.authentication.model.Session
import com.lukaszfabia.ferby.domain.authentication.usecase.ObserveSessionUseCase
import com.lukaszfabia.ferby.feature.home.HomeRoute
import com.lukaszfabia.ferby.feature.profile.ProfileRoute
import com.lukaszfabia.ferby.feature.search.SearchRoute
import com.lukaszfabia.ferby.main.model.toUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {
    private val testDispatcher = StandardTestDispatcher()
    
    private val observeSessionUseCase = FakeObserveSessionUseCase()
    private val navigationDelegate = FakeNavigationDelegateImpl()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun init_onAuthenticated_setsStateToAuthenticated() = runTest {
        // Given
        observeSessionUseCase.emit(Session(userId = "id"))

        // When
        val viewModel = MainViewModel(observeSessionUseCase, navigationDelegate)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertTrue(viewModel.state.value is MainState.Authenticated)
        val state = viewModel.state.value as MainState.Authenticated
        assertEquals(
            listOf(HomeRoute, ProfileRoute, SearchRoute).map { it.toUi() },
            state.tabsUi
        )
    }

    @Test
    fun init_onNotAuthenticated_setsStateToNotAuthenticated() = runTest {
        // Given
        observeSessionUseCase.emit(null)

        // When
        val viewModel = MainViewModel(observeSessionUseCase, navigationDelegate)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertEquals(MainState.NotAuthenticated, viewModel.state.value)
    }

    @Test
    fun handleEvent_onTabItemClick_updatesSelectedTabAndNavigates() = runTest {
        // Given
        observeSessionUseCase.emit(Session(userId = "id"))
        val viewModel = MainViewModel(observeSessionUseCase, navigationDelegate)
        testDispatcher.scheduler.advanceUntilIdle()
        val route = ProfileRoute

        // When
        viewModel.handleEvent(MainEvent.OnTabItemClick(route))
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val state = viewModel.state.value as MainState.Authenticated
        assertEquals(route, state.selectedTab)
        assertEquals(route, navigationDelegate.navigatedRoutes.last())
    }
}

private class FakeObserveSessionUseCase : ObserveSessionUseCase {
    private val flow = MutableStateFlow<Session?>(null)
    fun emit(session: Session?) {
        flow.value = session
    }
    override fun invoke(): Flow<Session?> = flow
}
