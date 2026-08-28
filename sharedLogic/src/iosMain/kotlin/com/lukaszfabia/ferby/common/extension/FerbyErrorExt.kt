package com.lukaszfabia.ferby.common.extension

import com.lukaszfabia.ferby.common.string.translate
import com.lukaszfabia.ferby.data.networking.model.ApiError
import com.lukaszfabia.ferby.data.networking.model.FerbyError

/** Used to map error in iOS. */
object FerbyErrorMapper {
    fun map(error: FerbyError): String =
        error.toLocalizedString()
}

/** Maps [FerbyError] subtypes to specific localized string. */
private fun FerbyError.toLocalizedString(): String =
    when(this) {
        is ApiError -> toLocalizedString()
        else -> translate(key = "unknown")
    }

/** Maps [ApiError] subtypes to specific localized string. */
private fun ApiError.toLocalizedString(): String =
    when(this) {
        is ApiError.NoDataError -> translate(key = "no_data_error")
        is ApiError.ClientError -> translate(key = "client_error")
        is ApiError.NotFound -> translate(key = "not_found")
        is ApiError.SerializationError -> translate(key = "serialization_error")
        is ApiError.ServerSideError -> translate(key = "server_side_error")
        is ApiError.Unauthorized -> translate(key = "unauthorized")
        is ApiError.Unknown -> translate(key = "unknown")
    }

