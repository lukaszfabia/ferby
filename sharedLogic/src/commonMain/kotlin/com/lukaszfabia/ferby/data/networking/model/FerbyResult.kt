package com.lukaszfabia.ferby.data.networking.model

/** Represents the result of some kind of call. It might be API call or database call. */
sealed class FerbyResult<out T> {
    /** Represents a successful API call.
     * @property [data] The data returned by the API call.
     * */
    data class Success<T>(
        val data: T,
    ) : FerbyResult<T>()

    /** Represents a failed API call.
     * @property [error] The data returned by the API call.
     * */
    data class Failure(
        val error: FerbyError,
    ) : FerbyResult<Nothing>()

    /** Performs transformations for each result type. Use to handle result. */
    fun <R> fold(
        onSuccess: (T) -> R,
        onFailure: (FerbyError) -> R,
    ): R =
        when (this) {
            is Success -> onSuccess(data)
            is Failure -> onFailure(error)
        }

    /** Gets data from [FerbyResult] when its [Success], returns null when [Failure] */
    fun <T> FerbyResult<T>.getOrDefault(default: T? = null): T? =
        when (this) {
            is Success<T> -> data
            is Failure -> default
        }

    /** Preprocesses [FerbyResult] using transformation. If result is [Failure] it wraps this error. */
    fun <R> map(transform: (T) -> R): FerbyResult<R> =
        when (this) {
            is Success -> Success(transform(data))
            is Failure -> Failure(error)
        }
}
