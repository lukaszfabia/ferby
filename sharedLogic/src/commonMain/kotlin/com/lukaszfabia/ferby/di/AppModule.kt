package com.lukaszfabia.ferby.di

import com.lukaszfabia.ferby.common.networking.getEngine
import com.lukaszfabia.ferby.core.networking.ApiClient
import com.lukaszfabia.ferby.core.networking.KtorClient
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNamingStrategy

@OptIn(ExperimentalSerializationApi::class)
object AppModule {
    val httpClient: ApiClient = KtorClient(
        client = HttpClient {
            engine {
                getEngine()
            }
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    namingStrategy = JsonNamingStrategy.SnakeCase
                    isLenient = true
                })
            }
            defaultRequest {
                contentType(ContentType.Application.Json)
            }
        }
    )
}