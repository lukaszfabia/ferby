package com.lukaszfabia.ferby.core.networking.extension

import com.lukaszfabia.ferby.core.result.ApiError
import com.lukaszfabia.ferby.core.result.FerbyError
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode

/** Converts [HttpResponse] to [ApiError]. */
fun HttpResponse.toError(): FerbyError =
    when (status) {
        HttpStatusCode.Unauthorized -> ApiError.Unauthorized
        HttpStatusCode.NotFound -> ApiError.NotFound
        in HttpStatusCode.BadRequest..HttpStatusCode.TooManyRequests -> ApiError.Client
        HttpStatusCode.InternalServerError -> ApiError.ServerSide
        else -> FerbyError.Unknown
    }
