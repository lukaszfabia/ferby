package com.lukaszfabia.ferby.feature.shrinesecrets.domain.usecase

import com.lukaszfabia.ferby.data.networking.model.FerbyResult
import com.lukaszfabia.ferby.feature.shrinesecrets.domain.model.ShrineSecrets
import com.lukaszfabia.ferby.feature.shrinesecrets.domain.repository.ShrineSecretsRepository

/** Use case for getting current shrine secrets. */
interface GetCurrentShrineSecretsUseCase {
    /** Returns current shrine secrets. */
    suspend operator fun invoke(): FerbyResult<ShrineSecrets>
}

/** An implementation of [GetCurrentShrineSecretsUseCase]. */
class GetCurrentShrineSecretsUseCaseImpl(
    private val repository: ShrineSecretsRepository,
) : GetCurrentShrineSecretsUseCase {
    override suspend fun invoke(): FerbyResult<ShrineSecrets> = repository.getCurrentShrineSecrets()
}
