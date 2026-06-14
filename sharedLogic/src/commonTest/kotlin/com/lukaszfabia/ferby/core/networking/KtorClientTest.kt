package com.lukaszfabia.ferby.core.networking

import com.lukaszfabia.ferby.data.networking.model.ApiError
import com.lukaszfabia.ferby.data.networking.model.ApiRequest
import com.lukaszfabia.ferby.data.networking.model.ApiResult
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class KtorClientTest {
    @Serializable
    data class TestData(
        val id: Int,
        val name: String,
    )

    private fun createClient(engine: MockEngine): HttpClient =
        HttpClient(engine) {
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                    },
                )
            }
        }

    @Test
    fun executeRaw_onSuccess_returnsResultWithResponse() =
        runTest {
            // Given
            val engine =
                MockEngine { _ ->
                    respond(
                        content = """{"id":1,"name":"Test"}""",
                        status = HttpStatusCode.OK,
                        headers = headersOf(HttpHeaders.ContentType, "application/json"),
                    )
                }
            val client = KtorClient(createClient(engine))
            val request =
                ApiRequest(
                    apiUrl = API_URL,
                    path = PATH,
                )

            // When
            val result = client.executeRaw(request)

            // Then
            assertTrue(result.isSuccess)
            assertEquals(HttpStatusCode.OK, result.getOrNull()?.status)
        }

    @Test
    fun executeRaw_onFailure_returnsFailureResult() =
        runTest {
            // Given
            val engine =
                MockEngine { _ ->
                    throw RuntimeException("Network error")
                }
            val client = KtorClient(createClient(engine))
            val request =
                ApiRequest(
                    apiUrl = API_URL,
                    path = PATH,
                )

            // When
            val result = client.executeRaw(request)

            // Then
            assertTrue(result.isFailure)
            assertEquals("Network error", result.exceptionOrNull()?.message)
        }

    @Test
    fun execute_onSuccess_returnsApiResultSuccess() =
        runTest {
            // Given
            val engine =
                MockEngine { _ ->
                    respond(
                        content = """{"id":1,"name":"Test"}""",
                        status = HttpStatusCode.OK,
                        headers = headersOf(HttpHeaders.ContentType, "application/json"),
                    )
                }
            val client = KtorClient(createClient(engine))
            val request =
                ApiRequest(
                    apiUrl = API_URL,
                    path = PATH,
                )

            // When
            val result: ApiResult<TestData> = client.execute(request)

            // Then
            val expected = ApiResult.Success(TestData(1, "Test"))
            assertEquals(expected, result)
        }

    @Test
    fun execute_onHttpError_returnsApiResultFailureWithCategorizedError() =
        runTest {
            // Given
            val engine =
                MockEngine { _ ->
                    respond(
                        content = "Not Found",
                        status = HttpStatusCode.NotFound,
                    )
                }
            val client = KtorClient(createClient(engine))
            val request =
                ApiRequest(
                    apiUrl = API_URL,
                    path = PATH,
                )

            // When
            val result: ApiResult<TestData> = client.execute(request)

            // Then
            assertTrue(result is ApiResult.Failure)
            assertEquals(ApiError.NotFound, result.error)
        }

    @Test
    fun execute_onSerializationError_returnsApiResultFailureWithSerializationError() =
        runTest {
            // Given
            val engine =
                MockEngine { _ ->
                    respond(
                        content = """{"id":"wrong_type","name":"Test"}""",
                        status = HttpStatusCode.OK,
                        headers = headersOf(HttpHeaders.ContentType, "application/json"),
                    )
                }
            val client = KtorClient(createClient(engine))
            val request =
                ApiRequest(
                    apiUrl = API_URL,
                    path = PATH,
                )

            // When
            val result: ApiResult<TestData> = client.execute(request)

            // Then
            assertTrue(result is ApiResult.Failure)
            assertEquals(ApiError.SerializationError, result.error)
        }

    @Test
    fun execute_onNetworkFailure_returnsApiResultFailureWithUnknownError() =
        runTest {
            // Given
            val engine =
                MockEngine { _ ->
                    throw RuntimeException("Connection failed")
                }
            val client = KtorClient(createClient(engine))
            val request =
                ApiRequest(
                    apiUrl = API_URL,
                    path = PATH,
                )

            // When
            val result: ApiResult<TestData> = client.execute(request)

            // Then
            assertTrue(result is ApiResult.Failure)
            assertEquals(ApiError.Unknown, result.error)
        }

    private companion object {
        const val API_URL = "http://fake-api"
        const val PATH = "test"
    }
}
