package com.lukaszfabia.ferby.feature.signin

import com.lukaszfabia.ferby.core.result.FerbyError

data class SignInState(
    val isLoading: Boolean = false,
    val error: FerbyError? = null,
)
