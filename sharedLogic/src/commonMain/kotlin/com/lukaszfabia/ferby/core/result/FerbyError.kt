package com.lukaszfabia.ferby.core.result

/** General type of the error which tags every kind of error that can occur. */
sealed interface FerbyError {
    data object Unknown : FerbyError
}

/**
 * Represents categorized errors that can occur during network API operations.
 */
sealed interface ApiError : FerbyError {
    data object Unauthorized : ApiError

    data object NotFound : ApiError

    data object Client : ApiError

    data object ServerSide : ApiError

    data object Serialization : ApiError

    data object NoData : ApiError
}

sealed interface GoogleSsoError : FerbyError {
    data object Cancelled : GoogleSsoError

    data object Configuration : GoogleSsoError

    data object MissingToken : GoogleSsoError

    data object SignInFailed : GoogleSsoError
}
