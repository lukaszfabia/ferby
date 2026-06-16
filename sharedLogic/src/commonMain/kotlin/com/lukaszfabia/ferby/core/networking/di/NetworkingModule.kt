package com.lukaszfabia.ferby.core.networking.di

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
import org.koin.dsl.module

@OptIn(ExperimentalSerializationApi::class)
val networkingModule = module {

    single {
        HttpClient {
            engine {
                getEngine()
            }

            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        namingStrategy = JsonNamingStrategy.SnakeCase
                        isLenient = true
                    }
                )
            }

            defaultRequest {
                contentType(ContentType.Application.Json)
            }
        }
    }

    single<ApiClient> {
        KtorClient(get())
    }
}