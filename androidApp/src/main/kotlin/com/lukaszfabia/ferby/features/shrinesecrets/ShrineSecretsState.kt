package com.lukaszfabia.ferby.features.shrinesecrets

import com.lukaszfabia.ferby.data.networking.model.Error
import com.lukaszfabia.ferby.feature.shrinesecrets.domain.model.ShrineSecrets

sealed interface ShrineSecretsState {
    data object Loading: ShrineSecretsState

    data class Failure(val error: Error): ShrineSecretsState

    data class Success(val shrineSecrets: ShrineSecrets): ShrineSecretsState
}
