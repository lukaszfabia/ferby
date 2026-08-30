package com.lukaszfabia.ferby.common.navigation

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class FakeNavigationDelegateImpl : NavigationDelegate {
    private val _events = MutableSharedFlow<NavigationEvent>(
        extraBufferCapacity = 1
    )

    override val events = _events.asSharedFlow()

    private val _navigatedRoutes = mutableListOf<FerbyRoute>()
    val navigatedRoutes: List<FerbyRoute> = _navigatedRoutes

    override suspend fun navigateBack() {
    }

    override suspend fun navigateHome() {
    }

    override suspend fun navigate(route: FerbyRoute) {
        _navigatedRoutes.add(route)
    }

    override suspend fun popStackBack(
        route: FerbyRoute,
        inclusive: Boolean
    ) {
    }
}