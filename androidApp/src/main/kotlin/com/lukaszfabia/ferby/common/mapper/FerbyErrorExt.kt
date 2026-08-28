package com.lukaszfabia.ferby.common.mapper

import androidx.annotation.StringRes
import com.lukaszfabia.ferby.R
import com.lukaszfabia.ferby.data.networking.model.ApiError
import com.lukaszfabia.ferby.data.networking.model.FerbyError

/** Maps [FerbyError] type to a string message. */
@StringRes
fun FerbyError.toMessageId(): Int =
    when(this) {
        is ApiError -> toMessageId()
        else -> R.string.unknown
    }

/** Maps [ApiError] type to a string message. */
@StringRes
fun ApiError.toMessageId(): Int =
    when(this) {
        is ApiError.Unauthorized -> R.string.unauthorized
        is ApiError.NotFound -> R.string.not_found
        is ApiError.ClientError -> R.string.client_error
        is ApiError.ServerSideError -> R.string.server_side_error
        is ApiError.SerializationError -> R.string.serialization_error
        is ApiError.NoDataError -> R.string.no_data_error
        is ApiError.Unknown -> R.string.unknown
    }