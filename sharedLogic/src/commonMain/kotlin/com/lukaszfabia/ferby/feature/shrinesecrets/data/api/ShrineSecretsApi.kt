package com.lukaszfabia.ferby.feature.shrinesecrets.data.api

import com.lukaszfabia.ferby.core.networking.ApiClient
import com.lukaszfabia.ferby.core.networking.execute
import com.lukaszfabia.ferby.data.networking.model.ApiResult
import com.lukaszfabia.ferby.feature.shrinesecrets.data.model.ShrineSecretsDto

/**
 * Interface defining the API endpoints for retrieving Shrine of Secrets information.
 */
interface ShrineSecretsApi {
    suspend fun getCurrentShrineSecrets(): ApiResult<ShrineSecretsDto>
}

/** An implementation of the [ShrineSecretsApi] interface.*/
class ShrineSecretsApiImpl(
    private val client: ApiClient,
) : ShrineSecretsApi {
    override suspend fun getCurrentShrineSecrets(): ApiResult<ShrineSecretsDto> =
        client.execute(ShrineSecretsApiRequestProvider.currentPromotion)
}
