package com.lukaszfabia.ferby.data.networking.model

/** Represents the result of an API call. */
sealed interface ApiResult<out T> {
    /** Represents a successful API call.
     * @property [data] The data returned by the API call.
     * */
    data class Success<T>(
        val data: T,
    ) : ApiResult<T>

    /** Represents a failed API call.
     * @property [error] The data returned by the API call.
     * */
    data class Failure(
        val error: Error,
    ) : ApiResult<Nothing>
}
