package com.lukaszfabia.ferby.domain.authentication.model

data class AuthenticatedUser(
    val userId: String,
    val email: String?,
    val displayName: String?,
    val photoUrl: String?,
    val providerId: String,
)

fun AuthenticatedUser.toSession(): Session = Session(userId)
