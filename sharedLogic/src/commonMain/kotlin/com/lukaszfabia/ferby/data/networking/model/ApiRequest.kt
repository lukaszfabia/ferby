package com.lukaszfabia.ferby.data.networking.model

import io.ktor.http.HttpMethod

/**
 * Represents the configuration for an outgoing HTTP network request.
 *
 * @property path The endpoint path or full URL for the request.
 * @property method The [HttpMethod] to be used (e.g., GET, POST). Defaults to [HttpMethod.Get].
 * @property headers A map of HTTP header keys and their corresponding values.
 * @property query A map of URL query parameters to be appended to the request.
 */
data class ApiRequest(
    val path: String,
    val method: HttpMethod = HttpMethod.Get,
    val headers: Map<String, String> = emptyMap(),
    val query: Map<String, String> = emptyMap(),
)