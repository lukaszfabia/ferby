package com.lukaszfabia.ferby.core.networking

import com.lukaszfabia.ferby.core.networking.extension.toError
import com.lukaszfabia.ferby.core.networking.extension.execute
import com.lukaszfabia.ferby.data.networking.model.ApiRequest
import com.lukaszfabia.ferby.data.networking.model.ApiResult
import com.lukaszfabia.ferby.data.networking.model.ApiError
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess
import io.ktor.serialization.JsonConvertException
import kotlinx.serialization.SerializationException

/**
 * Defines the contract for executing network requests.
 *
 * This interface provides a low-level mechanism to perform raw HTTP operations,
 * which can then be processed into type-safe results using extension functions.
 */
interface ApiClient {
    suspend fun executeRaw(request: ApiRequest): Result<HttpResponse>
}

/** Makes an api call and returns the result as a [ApiResult] */
suspend inline fun <reified T> ApiClient.execute(request: ApiRequest): ApiResult<T> =
    executeRaw(request).fold(
        onSuccess = { response ->
            try {
                if (response.status.isSuccess()) ApiResult.Success(response.body())
                else ApiResult.Failure(response.toError())
            } catch (e: Exception) {
                when (e) {
                    is JsonConvertException,
                    is SerializationException -> ApiResult.Failure(ApiError.SerializationError)
                    else -> ApiResult.Failure(ApiError.Unknown)
                }
            }
        },
        onFailure = { ApiResult.Failure(ApiError.Unknown) }
    )

/** An implementation of the [ApiClient] which uses Ktor */
class KtorClient(private val client: HttpClient) : ApiClient {
    override suspend fun executeRaw(request: ApiRequest): Result<HttpResponse> =
        runCatching { client.execute(request) }
}