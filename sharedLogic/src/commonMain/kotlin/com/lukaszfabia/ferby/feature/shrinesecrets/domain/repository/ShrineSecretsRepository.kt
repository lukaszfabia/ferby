package com.lukaszfabia.ferby.feature.shrinesecrets.domain.repository

import com.lukaszfabia.ferby.data.networking.model.FerbyResult
import com.lukaszfabia.ferby.feature.shrinesecrets.domain.model.ShrineSecrets

/** Repository for shrine secrets. */
interface ShrineSecretsRepository {
    /** Returns current shrine secrets promotion. */
    suspend fun getCurrentShrineSecrets(): FerbyResult<ShrineSecrets>
}
