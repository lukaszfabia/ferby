package com.lukaszfabia.ferby.data.user.model

import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Serializable
data class FirestoreUser(
    val email: String? = null,
    val name: String? = null,
    val providerId: String,
    val photoUrl: String? = null,
    val username: String? = null,
    val createdAt: Instant,
)
