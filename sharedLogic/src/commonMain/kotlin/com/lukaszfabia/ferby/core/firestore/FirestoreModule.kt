package com.lukaszfabia.ferby.core.firestore

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import org.koin.dsl.module

val firestoreModule =
    module {
        single {
            Firebase.firestore
        }

        single {
            SafeFirestoreCaller()
        }
    }
