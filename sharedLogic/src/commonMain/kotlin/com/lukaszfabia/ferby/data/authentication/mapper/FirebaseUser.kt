package com.lukaszfabia.ferby.data.authentication.mapper

import com.lukaszfabia.ferby.domain.authentication.model.AuthenticatedUser
import dev.gitlive.firebase.auth.FirebaseUser

fun FirebaseUser.toDomain(): AuthenticatedUser =
    AuthenticatedUser(
        userId = this.uid,
        email = this.email,
        displayName = this.displayName,
        photoUrl = this.photoURL,
        providerId = this.providerId,
    )
