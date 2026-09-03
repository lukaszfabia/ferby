package com.lukaszfabia.ferby.data.user.mapper

import com.lukaszfabia.ferby.data.user.model.FirestoreUser
import com.lukaszfabia.ferby.domain.user.model.User

fun User.toFirestore(): FirestoreUser =
    FirestoreUser(
        email = email,
        name = name,
        providerId = providerId,
        photoUrl = photoUrl,
        username = username,
        createdAt = createdAt,
    )
