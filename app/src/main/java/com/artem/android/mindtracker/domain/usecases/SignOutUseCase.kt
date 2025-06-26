package com.artem.android.mindtracker.domain.usecases

import com.artem.android.mindtracker.domain.repositories.AuthRepository
import javax.inject.Inject

class SignOutUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke() {
        return authRepository.signOut()
    }
}
