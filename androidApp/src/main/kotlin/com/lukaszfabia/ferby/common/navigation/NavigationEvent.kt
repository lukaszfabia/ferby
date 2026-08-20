package com.lukaszfabia.ferby.common.navigation

/**
 * Represents navigation events.
 */
sealed interface NavigationEvent {
    /**
     * Navigation event to navigate to a specific [route].
     */
    data class Navigate(val route: FerbyRoute) : NavigationEvent

    /**
     * Navigation event to pop the stack back to a specific [route].
     *
     * @param route The route to pop back to.
     * @param inclusive Whether to include the [route] in the pop operation.
     */
    data class PopStackBack(val route: FerbyRoute, val inclusive: Boolean = false) : NavigationEvent

    /**
     * Navigation event to navigate home.
     */
    data object NavigateHome : NavigationEvent

    /**
     * Navigation event to navigate back.
     */
    data object NavigateBack : NavigationEvent
}