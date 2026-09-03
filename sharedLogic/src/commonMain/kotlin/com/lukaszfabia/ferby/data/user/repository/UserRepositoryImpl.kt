package com.lukaszfabia.ferby.data.user.repository

import com.lukaszfabia.ferby.core.result.FerbyResult
import com.lukaszfabia.ferby.data.user.api.UserApi
import com.lukaszfabia.ferby.data.user.mapper.toDomain
import com.lukaszfabia.ferby.data.user.mapper.toFirestore
import com.lukaszfabia.ferby.domain.authentication.model.AuthenticatedUser
import com.lukaszfabia.ferby.domain.user.model.User
import com.lukaszfabia.ferby.domain.user.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserRepositoryImpl(
    private val api: UserApi,
) : UserRepository {
    override suspend fun getUser(userId: String): FerbyResult<User?> = api.getUser(userId).map { it?.toDomain(userId) }

    override fun observeUser(userId: String): Flow<User?> = api.observeUser(userId).map { it?.toDomain(userId) }

    override suspend fun createUser(authenticatedUser: AuthenticatedUser): FerbyResult<User> =
        api
            .saveUser(authenticatedUser.userId, authenticatedUser.toFirestore())
            .map { it.toDomain(authenticatedUser.userId) }

    override suspend fun saveUser(user: User): FerbyResult<User> = api.saveUser(user.id, user.toFirestore()).map { it.toDomain(user.id) }
}
