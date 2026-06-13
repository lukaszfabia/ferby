package com.lukaszfabia.ferby.feature.shrinesecrets.domain.repository

import com.lukaszfabia.ferby.data.networking.model.ApiResult
import com.lukaszfabia.ferby.feature.shrinesecrets.domain.model.ShrineSecrets

class FakeShrineSecretsRepository : ShrineSecretsRepository {
    var result: ApiResult<ShrineSecrets>? = null

    override suspend fun getCurrentShrineSecrets(): ApiResult<ShrineSecrets> {
        return result ?: throw IllegalStateException("Result not set in FakeShrineSecretsRepository")
    }
}