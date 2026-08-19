package com.lukaszfabia.ferby.data.networking.model

/** Represents the result of some kind of call. It might be API call or database call. */
sealed interface FerbyResult<out T> {
    /** Represents a successful API call.
     * @property [data] The data returned by the API call.
     * */
    data class Success<T>(
        val data: T,
    ) : FerbyResult<T>

    /** Represents a failed API call.
     * @property [error] The data returned by the API call.
     * */
    data class Failure(
        val error: FerbyError,
    ) : FerbyResult<Nothing>
}
