package com.lukaszfabia.ferby.domain.user.repository

import com.lukaszfabia.ferby.core.result.FerbyResult
import com.lukaszfabia.ferby.domain.authentication.model.AuthenticatedUser
import com.lukaszfabia.ferby.domain.user.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlin.time.Clock

class FakeUserRepository : UserRepository {
    private val users = MutableStateFlow<Map<String, User>>(emptyMap())

    override suspend fun getUser(userId: String): FerbyResult<User?> = FerbyResult.Success(users.value[userId])

    override fun observeUser(userId: String): Flow<User?> = users.map { it[userId] }

    override suspend fun createUser(authenticatedUser: AuthenticatedUser): FerbyResult<User> {
        val user =
            User(
                id = authenticatedUser.userId,
                email = authenticatedUser.email,
                name = authenticatedUser.displayName,
                providerId = authenticatedUser.providerId,
                photoUrl = authenticatedUser.photoUrl,
                username = null,
                createdAt = Clock.System.now(),
            )
        users.value += (user.id to user)
        return FerbyResult.Success(user)
    }

    override suspend fun saveUser(user: User): FerbyResult<User> {
        users.value += (user.id to user)
        return FerbyResult.Success(user)
    }
}
