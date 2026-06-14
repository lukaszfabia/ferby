package com.lukaszfabia.ferby.data.networking.model

import io.ktor.http.HttpMethod
import io.ktor.http.URLBuilder
import io.ktor.http.Url
import io.ktor.http.encodedPath

/**
 * Represents the configuration for an outgoing HTTP network request.
 *
 * @property apiUrl Url of the API.
 * @property path The endpoint path for the request.
 * @property method The [HttpMethod] to be used (e.g., GET, POST). Defaults to [HttpMethod.Get].
 * @property headers A map of HTTP header keys and their corresponding values.
 * @property query A map of URL query parameters to be appended to the request.
 */
data class ApiRequest(
    val apiUrl: String,
    val path: String,
    val method: HttpMethod = HttpMethod.Get,
    val headers: Map<String, String> = emptyMap(),
    val query: Map<String, String> = emptyMap(),
) {
    /** Build URL from apiUrl and endpoint path. */
    val url: Url
        get() = URLBuilder(apiUrl).apply {
            encodedPath = encodedPath.trimEnd('/') + "/" + path.trimStart('/')
        }.build()
}