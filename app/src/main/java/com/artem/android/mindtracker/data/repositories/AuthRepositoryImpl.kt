package com.artem.android.mindtracker.data.repositories

import com.artem.android.mindtracker.domain.repositories.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
): AuthRepository {
    override suspend fun signUp(email: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).await()
    }

    override suspend fun signIn(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password).await()
    }

    override suspend fun signOut() {
        firebaseAuth.signOut()
    }

    override suspend fun isUserAuthenticated(): Boolean {
        return firebaseAuth.currentUser != null
    }
}
