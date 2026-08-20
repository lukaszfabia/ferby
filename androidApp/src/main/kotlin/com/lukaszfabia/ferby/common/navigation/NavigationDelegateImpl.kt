package com.lukaszfabia.ferby.common.navigation

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

/**
 * Implementation of [NavigationDelegate] that handles navigation actions.
 */
class NavigationDelegateImpl: NavigationDelegate {
    private val _events = MutableSharedFlow<NavigationEvent>(
        extraBufferCapacity = 1
    )

    override val events = _events.asSharedFlow()

    /**
     * Emits a [NavigationEvent.NavigateBack] event.
     */
    override suspend fun navigateBack() {
        _events.emit(NavigationEvent.NavigateBack)
    }

    /**
     * Emits a [NavigationEvent.NavigateHome] event.
     */
    override suspend fun navigateHome() {
        _events.emit(NavigationEvent.NavigateHome)
    }

    /**
     * Emits a [NavigationEvent.Navigate] event with the specified [route].
     */
    override suspend fun navigate(route: FerbyRoute) {
        _events.emit(NavigationEvent.Navigate(route))
    }

    /**
     * Emits a [NavigationEvent.PopStackBack] event with the specified [route] and [inclusive] flag.
     */
    override suspend fun popStackBack(
        route: FerbyRoute,
        inclusive: Boolean
    ) {
        _events.emit(NavigationEvent.PopStackBack(route, inclusive))
    }
}