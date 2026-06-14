package com.lukaszfabia.ferby.core.networking.extension

import com.lukaszfabia.ferby.data.networking.model.ApiError
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode

fun HttpResponse.toError(): ApiError =
    when (status) {
        HttpStatusCode.Unauthorized -> ApiError.Unauthorized
        HttpStatusCode.NotFound -> ApiError.NotFound
        in HttpStatusCode.BadRequest..HttpStatusCode.TooManyRequests -> ApiError.ClientError
        HttpStatusCode.InternalServerError -> ApiError.ServerSideError
        else -> ApiError.Unknown
    }