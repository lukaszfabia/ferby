package com.lukaszfabia.ferby.data.user.mapper

import com.lukaszfabia.ferby.data.user.model.FirestoreUser
import com.lukaszfabia.ferby.domain.user.model.User

fun FirestoreUser.toDomain(id: String): User =
    User(
        id = id,
        email = email,
        name = name,
        providerId = providerId,
        photoUrl = photoUrl,
        username = username,
        createdAt = createdAt,
    )
