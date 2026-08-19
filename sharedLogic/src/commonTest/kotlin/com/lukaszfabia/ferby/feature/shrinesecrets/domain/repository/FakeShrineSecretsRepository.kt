package com.lukaszfabia.ferby.feature.shrinesecrets.domain.repository

import com.lukaszfabia.ferby.data.networking.model.FerbyResult
import com.lukaszfabia.ferby.feature.shrinesecrets.domain.model.ShrineSecrets

class FakeShrineSecretsRepository : ShrineSecretsRepository {
    var result: FerbyResult<ShrineSecrets>? = null

    override suspend fun getCurrentShrineSecrets(): FerbyResult<ShrineSecrets> =
        result ?: throw IllegalStateException("Result not set in FakeShrineSecretsRepository")
}
