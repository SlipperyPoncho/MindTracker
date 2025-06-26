package com.artem.android.mindtracker.domain.repositories

interface AuthRepository {
    suspend fun signIn(email: String, password: String)
    suspend fun signUp(email: String, password: String)
    suspend fun signOut()
    suspend fun isUserAuthenticated(): Boolean
}
