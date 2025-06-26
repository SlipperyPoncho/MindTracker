package com.artem.android.mindtracker.domain.usecases

import com.artem.android.mindtracker.domain.repositories.AuthRepository
import javax.inject.Inject

class AuthenticationCheckUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Boolean {
        return authRepository.isUserAuthenticated()
    }
}
