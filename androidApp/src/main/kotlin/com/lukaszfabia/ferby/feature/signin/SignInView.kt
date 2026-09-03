package com.lukaszfabia.ferby.feature.signin

import android.app.Activity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.lukaszfabia.ferby.common.mapper.toMessageId

@Composable
fun SignInView(
    state: SignInState,
    eventHandler: SignInEventHandler
) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { scaffoldPadding ->
        val context = LocalContext.current
        Column(
            modifier = Modifier.fillMaxSize().padding(scaffoldPadding)
        ) {
            if (state.isLoading) {
                Text("Loading...")
            } else {
                Button(
                    onClick = {
                        val activity = context as? Activity
                        if (activity != null) {
                            eventHandler.invoke(SignInEvent.OnSignInWithGoogleClick(activity))
                        }
                    }
                ) {
                    Text("Sign with google")
                }

                if (state.error != null) {
                    Text(text = stringResource(state.error.toMessageId()))
                }
            }
        }
    }
}