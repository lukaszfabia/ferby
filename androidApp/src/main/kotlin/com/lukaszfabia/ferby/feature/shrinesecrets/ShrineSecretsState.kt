package com.lukaszfabia.ferby.feature.shrinesecrets

import com.lukaszfabia.ferby.data.networking.model.FerbyError
import com.lukaszfabia.ferby.feature.shrinesecrets.domain.model.ShrineSecrets

/**
 * Represents the state of the shrine secrets feature.
 */
sealed interface ShrineSecretsState {
    /**
     * Indicates that the shrine secrets are currently being loaded.
     */
    data object Loading: ShrineSecretsState

    /**
     * Indicates that loading the shrine secrets has failed.
     *
     * @property error The error that occurred during loading.
     */
    data class Failure(val error: FerbyError): ShrineSecretsState

    /**
     * Indicates that the shrine secrets have been loaded successfully.
     *
     * @property shrineSecrets The loaded shrine secrets.
     */
    data class Success(val shrineSecrets: ShrineSecrets): ShrineSecretsState
}
