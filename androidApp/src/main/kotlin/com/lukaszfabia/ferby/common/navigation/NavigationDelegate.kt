package com.lukaszfabia.ferby.common.navigation

import kotlinx.coroutines.flow.SharedFlow

/**
 * Handles navigation actions and provides navigation events.
 */
interface NavigationDelegate {
    /**
     * Provides navigation events.
     */
    val events: SharedFlow<NavigationEvent>
    
    /**
     * Navigates back in the navigation stack.
     */
    suspend fun navigateBack()
    
    /**
     * Navigates to the home screen.
     */
    suspend fun navigateHome()
    
    /**
     * Navigates to the specified [route].
     */
    suspend fun navigate(route: FerbyRoute)
    
    /**
     * Pops the navigation stack back to the specified [route].
     *
     * @param route The route to pop back to.
     * @param inclusive Whether to include the [route] in the pop operation.
     */
    suspend fun popStackBack(route: FerbyRoute, inclusive: Boolean = false)
}