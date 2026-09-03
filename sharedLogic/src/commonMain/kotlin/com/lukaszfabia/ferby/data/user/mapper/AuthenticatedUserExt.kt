package com.lukaszfabia.ferby.data.user.mapper

import com.lukaszfabia.ferby.data.user.model.FirestoreUser
import com.lukaszfabia.ferby.domain.authentication.model.AuthenticatedUser
import kotlin.time.Clock

fun AuthenticatedUser.toFirestore(): FirestoreUser =
    FirestoreUser(
        email = email,
        name = displayName,
        providerId = providerId,
        photoUrl = photoUrl,
        createdAt = Clock.System.now(),
    )
