package com.lukaszfabia.ferby.core.firestore

import com.lukaszfabia.ferby.core.result.ApiError
import com.lukaszfabia.ferby.core.result.FerbyError
import com.lukaszfabia.ferby.core.result.FerbyResult
import dev.gitlive.firebase.firestore.FirebaseFirestoreException
import dev.gitlive.firebase.firestore.FirestoreExceptionCode
import dev.gitlive.firebase.firestore.code
import kotlinx.serialization.SerializationException

class SafeFirestoreCaller {
    suspend operator fun <T> invoke(action: suspend () -> T): FerbyResult<T> =
        try {
            FerbyResult.Success(action())
        } catch (e: FirebaseFirestoreException) {
            FerbyResult.Failure(e.toFerbyError())
        } catch (_: SerializationException) {
            FerbyResult.Failure(ApiError.Serialization)
        } catch (_: Exception) {
            FerbyResult.Failure(FerbyError.Unknown)
        }
}

fun FirebaseFirestoreException.toFerbyError(): FerbyError =
    when (code) {
        FirestoreExceptionCode.PERMISSION_DENIED ->
            ApiError.Unauthorized

        FirestoreExceptionCode.NOT_FOUND ->
            ApiError.NotFound

        FirestoreExceptionCode.UNAVAILABLE,
        FirestoreExceptionCode.DEADLINE_EXCEEDED,
        ->
            ApiError.ServerSide

        else ->
            FerbyError.Unknown
    }
