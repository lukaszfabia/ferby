package com.lukaszfabia.ferby.core.networking.extension

import com.lukaszfabia.ferby.data.networking.model.ApiRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.header
import io.ktor.client.request.request
import io.ktor.client.statement.HttpResponse

suspend fun HttpClient.execute(request: ApiRequest): HttpResponse =
    request(request.path) {
        method = request.method
        request.headers.forEach { header(it.key, it.value) }
        url {
            request.query.forEach { parameters.append(it.key, it.value) }
        }
    }