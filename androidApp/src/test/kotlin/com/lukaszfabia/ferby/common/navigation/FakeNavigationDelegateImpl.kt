package com.lukaszfabia.ferby.common.navigation

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class FakeNavigationDelegateImpl : NavigationDelegate {
    private val _events = MutableSharedFlow<NavigationEvent>(
        extraBufferCapacity = 1
    )

    override val events = _events.asSharedFlow()

    override suspend fun navigateBack() {
    }

    override suspend fun navigateHome() {
    }

    override suspend fun navigate(route: FerbyRoute) {
    }

    override suspend fun popStackBack(
        route: FerbyRoute,
        inclusive: Boolean
    ) {
    }
}