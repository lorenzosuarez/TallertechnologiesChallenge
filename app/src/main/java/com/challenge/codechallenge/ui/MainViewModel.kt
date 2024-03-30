package com.challenge.codechallenge.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val correctUser = "user"
    private val correctPass = "pass"

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

    fun login(user: String, password: String) {
        viewModelScope.launch {
            if (user.isBlank() || password.isBlank()) {
                _loginState.update { LoginState.Error("User or password not valid") }
            } else {
                _loginState.update { LoginState.Loadingg }

                delay(4000)

                val newState = if (user == correctUser && password == correctPass) {
                    LoginState.Success
                } else {
                    LoginState.Error("INvalid credentials")
                }

                _loginState.value = newState
            }
        }
    }
}