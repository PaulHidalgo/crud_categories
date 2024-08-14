package com.phidalgo.crudcategories.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.phidalgo.crudcategories.data.network.auth.AuthRepository
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _loginResult = MutableLiveData<AuthResult?>()
    val loginResult: LiveData<AuthResult?> get() = _loginResult

    private val _registerResult = MutableLiveData<AuthResult?>()
    val registerResult: LiveData<AuthResult?> get() = _registerResult

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun login(email: String, password: String) {
        viewModelScope.launch {
            val result = authRepository.login(email, password)
            result.onSuccess { authResult ->
                _loginResult.value = authResult
            }.onFailure { exception ->
                if (exception is FirebaseAuthInvalidCredentialsException){
                    _errorMessage.value = exception.errorCode
                } else {
                    _errorMessage.value = exception.message ?: "An unknown error occurred"
                }

            }
        }
    }

    fun register(email: String, password: String) {
        viewModelScope.launch {
            val result = authRepository.register(email, password)
            result.onSuccess { authResult ->
                _registerResult.value = authResult
            }.onFailure { exception ->
                if (exception is FirebaseAuthInvalidCredentialsException){
                    _errorMessage.value = exception.errorCode
                } else {
                    _errorMessage.value = exception.message ?: "An unknown error occurred"
                }
            }
        }
    }
}
