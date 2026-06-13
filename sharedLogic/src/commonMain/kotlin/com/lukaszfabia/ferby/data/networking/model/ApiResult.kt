package com.lukaszfabia.ferby.data.networking.model

/** Represents the result of an API call. */
sealed interface ApiResult<T> {

    /** Represents a successful API call.
     * @property [data] The data returned by the API call.
     * */
    data class Success<T>(val data: T) : ApiResult<T>

    /** Represents a failed API call.
     * @property [message] The data returned by the API call.
     * */
    data class Error<T>(val message: String) : ApiResult<T>
}