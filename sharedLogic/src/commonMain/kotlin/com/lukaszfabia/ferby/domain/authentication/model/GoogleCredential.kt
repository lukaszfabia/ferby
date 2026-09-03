package com.lukaszfabia.ferby.domain.authentication.model

/** Information about user that finished whole getting token flow. */
data class GoogleCredential(
    val idToken: String,
    val accessToken: String?,
)
