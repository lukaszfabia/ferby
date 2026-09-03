package com.lukaszfabia.ferby.feature.shrinesecrets

import com.lukaszfabia.ferby.domain.deadbydaylight.model.Perk

/**
 * Type alias for a function that handles [ShrineSecretsEvent] instances.
 */
typealias ShrineSecretsEventHandler = (ShrineSecretsEvent) -> Unit

/**
 * Sealed interface representing events that can occur in the shrine secrets feature.
 */
sealed interface ShrineSecretsEvent {
    /**
     * Event triggered when the [Perk] is clicked.
     */
    data class OnPerkClick(val perk: Perk): ShrineSecretsEvent
}