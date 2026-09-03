package com.lukaszfabia.ferby.domain.user.model

import kotlin.time.Instant

data class User(
    val id: String,
    val email: String?,
    val name: String?,
    val providerId: String,
    val photoUrl: String?,
    val username: String?,
    val createdAt: Instant,
)
