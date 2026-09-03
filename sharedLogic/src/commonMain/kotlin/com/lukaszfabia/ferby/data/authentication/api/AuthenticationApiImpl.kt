package com.lukaszfabia.ferby.data.authentication.api

import com.lukaszfabia.ferby.core.firestore.toFerbyError
import com.lukaszfabia.ferby.core.result.ApiError
import com.lukaszfabia.ferby.core.result.FerbyError
import com.lukaszfabia.ferby.core.result.FerbyResult
import com.lukaszfabia.ferby.domain.authentication.model.GoogleCredential
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.FirebaseUser
import dev.gitlive.firebase.auth.GoogleAuthProvider
import dev.gitlive.firebase.firestore.FirebaseFirestoreException

class AuthenticationApiImpl(
    private val firebaseAuth: FirebaseAuth,
) : AuthenticationApi {
    override val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    override suspend fun authenticateWithGoogle(credential: GoogleCredential): FerbyResult<FirebaseUser> {
        try {
            val authCredential =
                GoogleAuthProvider.credential(
                    idToken = credential.idToken,
                    accessToken = credential.accessToken,
                )

            val result = firebaseAuth.signInWithCredential(authCredential)
            val user = result.user

            if (user != null) {
                return FerbyResult.Success(user)
            }
            return FerbyResult.Failure(error = ApiError.NoData)
        } catch (e: FirebaseFirestoreException) {
            return FerbyResult.Failure(error = e.toFerbyError())
        } catch (_: Exception) {
            return FerbyResult.Failure(error = FerbyError.Unknown)
        }
    }

    override suspend fun signOut() {
        firebaseAuth.signOut()
    }
}
