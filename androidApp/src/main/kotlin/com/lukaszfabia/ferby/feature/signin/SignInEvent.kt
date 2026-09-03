package com.lukaszfabia.ferby.feature.signin

import android.app.Activity

typealias SignInEventHandler = (SignInEvent) -> Unit

sealed interface SignInEvent {
    data class OnSignInWithGoogleClick(val activity: Activity): SignInEvent
}