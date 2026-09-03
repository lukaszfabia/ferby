package com.lukaszfabia.ferby.data.user.api

import com.lukaszfabia.ferby.core.result.FerbyResult
import com.lukaszfabia.ferby.data.user.model.FirestoreUser
import kotlinx.coroutines.flow.Flow

interface UserApi {
    suspend fun getUser(userId: String): FerbyResult<FirestoreUser?>

    fun observeUser(userId: String): Flow<FirestoreUser?>

    suspend fun saveUser(
        userId: String,
        firestoreUser: FirestoreUser,
    ): FerbyResult<FirestoreUser>
}
