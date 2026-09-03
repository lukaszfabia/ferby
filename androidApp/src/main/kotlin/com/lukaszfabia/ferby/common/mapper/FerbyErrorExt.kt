package com.lukaszfabia.ferby.common.mapper

import androidx.annotation.StringRes
import com.lukaszfabia.ferby.R
import com.lukaszfabia.ferby.core.result.ApiError
import com.lukaszfabia.ferby.core.result.FerbyError
import com.lukaszfabia.ferby.core.result.GoogleSsoError

/** Maps [FerbyError] type to a string message. */
@StringRes
fun FerbyError.toMessageId(): Int =
    when(this) {
        is ApiError -> toMessageId()
        is GoogleSsoError -> toMessageId()
        else -> R.string.unknown
    }

/** Maps [GoogleSsoError] type to a string message. */
@StringRes
fun GoogleSsoError.toMessageId(): Int =
    when(this) {
        GoogleSsoError.Cancelled -> R.string.cancelled
        GoogleSsoError.Configuration -> R.string.configuration
        GoogleSsoError.MissingToken -> R.string.missing_token
        GoogleSsoError.SignInFailed -> R.string.sign_in_failed
    }

/** Maps [ApiError] type to a string message. */
@StringRes
fun ApiError.toMessageId(): Int =
    when(this) {
        is ApiError.Unauthorized -> R.string.unauthorized
        is ApiError.NotFound -> R.string.not_found
        is ApiError.Client -> R.string.client_error
        is ApiError.ServerSide -> R.string.server_side_error
        is ApiError.Serialization -> R.string.serialization_error
        is ApiError.NoData -> R.string.no_data_error
    }