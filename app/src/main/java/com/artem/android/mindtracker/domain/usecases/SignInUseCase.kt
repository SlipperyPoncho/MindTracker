package com.artem.android.mindtracker.domain.usecases

import com.artem.android.mindtracker.domain.repositories.AuthRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String) {
        return authRepository.signIn(email, password)
    }
}
