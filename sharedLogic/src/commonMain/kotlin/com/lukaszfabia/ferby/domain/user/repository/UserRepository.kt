package com.lukaszfabia.ferby.domain.user.repository

import com.lukaszfabia.ferby.core.result.FerbyResult
import com.lukaszfabia.ferby.domain.authentication.model.AuthenticatedUser
import com.lukaszfabia.ferby.domain.user.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getUser(userId: String): FerbyResult<User?>

    fun observeUser(userId: String): Flow<User?>

    suspend fun createUser(authenticatedUser: AuthenticatedUser): FerbyResult<User>

    suspend fun saveUser(user: User): FerbyResult<User>
}
