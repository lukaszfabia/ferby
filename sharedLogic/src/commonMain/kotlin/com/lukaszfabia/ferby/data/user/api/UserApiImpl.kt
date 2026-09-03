package com.lukaszfabia.ferby.data.user.api

import com.lukaszfabia.ferby.core.firestore.SafeFirestoreCaller
import com.lukaszfabia.ferby.core.result.FerbyResult
import com.lukaszfabia.ferby.data.user.model.FirestoreUser
import dev.gitlive.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserApiImpl(
    private val firestore: FirebaseFirestore,
    private val safeFirestoreCaller: SafeFirestoreCaller,
) : UserApi {
    override suspend fun getUser(userId: String): FerbyResult<FirestoreUser?> =
        safeFirestoreCaller {
            firestore.collection(USERS).document(userId).get().let { snapshot ->
                if (snapshot.exists) {
                    snapshot.data<FirestoreUser>()
                } else {
                    null
                }
            }
        }

    override fun observeUser(userId: String): Flow<FirestoreUser?> =
        firestore.collection(USERS).document(userId).snapshots.map { snapshot ->
            if (snapshot.exists) {
                snapshot.data<FirestoreUser>()
            } else {
                null
            }
        }

    override suspend fun saveUser(
        userId: String,
        firestoreUser: FirestoreUser,
    ): FerbyResult<FirestoreUser> =
        safeFirestoreCaller {
            firestore.collection(USERS).document(userId).set(firestoreUser)
            firestoreUser
        }

    private companion object {
        const val USERS = "users"
    }
}
