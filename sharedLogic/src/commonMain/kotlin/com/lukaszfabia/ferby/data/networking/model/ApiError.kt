package com.lukaszfabia.ferby.data.networking.model

/**
 * Represents categorized errors that can occur during network API operations.
 */
sealed interface ApiError : FerbyError {
    data object Unauthorized : ApiError

    data object NotFound : ApiError

    data object ClientError : ApiError

    data object ServerSideError : ApiError

    data object SerializationError : ApiError

    data object Unknown : ApiError
}
