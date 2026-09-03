package com.lukaszfabia.ferby.feature.shrinesecrets.data.repository

import com.lukaszfabia.ferby.core.result.FerbyResult
import com.lukaszfabia.ferby.feature.shrinesecrets.data.api.ShrineSecretsApi
import com.lukaszfabia.ferby.feature.shrinesecrets.data.model.toDomain
import com.lukaszfabia.ferby.feature.shrinesecrets.domain.model.ShrineSecrets
import com.lukaszfabia.ferby.feature.shrinesecrets.domain.repository.ShrineSecretsRepository

/** An implementation of [ShrineSecretsRepository] that fetches shrine secrets data from an API.*/
class ShrineSecretsRepositoryImpl(
    private val api: ShrineSecretsApi,
) : ShrineSecretsRepository {
    override suspend fun getCurrentShrineSecrets(): FerbyResult<ShrineSecrets> = api.getCurrentShrineSecrets().map { it.toDomain() }
}
