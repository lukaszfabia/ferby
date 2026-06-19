package com.lukaszfabia.ferby.feature.shrinesecrets

import com.lukaszfabia.ferby.data.networking.model.Error
import com.lukaszfabia.ferby.feature.shrinesecrets.domain.model.ShrineSecrets

/** Represents the state of the Shrine Secrets feature. */
sealed interface ShrineSecretsState {
    /** Represents the initial state of the Shrine Secrets feature - loading. */
    data object Loading: ShrineSecretsState

    /** Represents error state of the Shrine Secrets feature.
     * @property error Error that occurred
     * */
    data class Failure(val error: Error): ShrineSecretsState


    /** Represents success state of the Shrine Secrets feature.
     * @property shrineSecrets ShrineSecrets that was successfully fetched
     * */
    data class Success(val shrineSecrets: ShrineSecrets): ShrineSecretsState
}
