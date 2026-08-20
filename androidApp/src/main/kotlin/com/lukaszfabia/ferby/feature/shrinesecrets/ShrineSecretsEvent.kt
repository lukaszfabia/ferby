package com.lukaszfabia.ferby.feature.shrinesecrets

/**
 * Type alias for a function that handles [ShrineSecretsEvent] instances.
 */
typealias ShrineSecretsEventHandler = (ShrineSecretsEvent) -> Unit

/**
 * Sealed interface representing events that can occur in the shrine secrets feature.
 */
sealed interface ShrineSecretsEvent {
    /**
     * Event triggered when the button is clicked.
     */
    data object OnButtonClick: ShrineSecretsEvent
}