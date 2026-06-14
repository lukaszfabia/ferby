package com.lukaszfabia.ferby.feature.shrinesecrets.domain.usecase

import com.lukaszfabia.ferby.data.networking.model.ApiResult
import com.lukaszfabia.ferby.feature.shrinesecrets.domain.model.ShrineSecrets
import com.lukaszfabia.ferby.feature.shrinesecrets.domain.repository.ShrineSecretsRepository

/** Use case for getting current shrine secrets. */
interface GetCurrentShrineSecretsUseCase {
    /** Returns current shrine secrets. */
    suspend operator fun invoke(): ApiResult<ShrineSecrets>
}

/** An implementation of [GetCurrentShrineSecretsUseCase]. */
class GetCurrentShrineSecretsUseCaseImpl(
    private val repository: ShrineSecretsRepository,
) : GetCurrentShrineSecretsUseCase {
    override suspend fun invoke(): ApiResult<ShrineSecrets> = repository.getCurrentShrineSecrets()
}
