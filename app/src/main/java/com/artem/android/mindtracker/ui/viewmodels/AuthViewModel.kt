package com.artem.android.mindtracker.ui.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artem.android.mindtracker.domain.usecases.AuthenticationCheckUseCase
import com.artem.android.mindtracker.domain.usecases.SignInUseCase
import com.artem.android.mindtracker.domain.usecases.SignOutUseCase
import com.artem.android.mindtracker.domain.usecases.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    object Authenticated : AuthState()
    object Unauthenticated : AuthState()
    data class Error(val msg: String) : AuthState()
}

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val signUpUseCase: SignUpUseCase,
    private val signOutUseCase: SignOutUseCase,
    private val authenticationCheckUseCase: AuthenticationCheckUseCase
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    val email = mutableStateOf("")
    val password = mutableStateOf("")

    fun signIn() {
        _authState.value = AuthState.Loading
        viewModelScope.launch {
            try {
                signInUseCase(email.value, password.value)
                _authState.value = AuthState.Authenticated
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun signUp() {
        _authState.value = AuthState.Loading
        viewModelScope.launch {
            try {
                signUpUseCase(email.value, password.value)
                _authState.value = AuthState.Authenticated
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "UnknownError")
            }
        }
    }

    fun signOut() {
        _authState.value = AuthState.Loading
        viewModelScope.launch {
            try {
                signOutUseCase()
                _authState.value = AuthState.Unauthenticated
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun checkUserAuthenticated() {
        viewModelScope.launch {
            try {
                if (authenticationCheckUseCase()) {
                    _authState.value = AuthState.Authenticated
                } else {
                    _authState.value = AuthState.Unauthenticated
                }
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "UnknownError")
            }
        }
    }
}
