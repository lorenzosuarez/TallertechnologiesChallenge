package com.challenge.codechallenge.ui

sealed class LoginState {
    data object Idle: LoginState()
    data object Success: LoginState()
    data object Loadingg: LoginState()
    data class Error(val error: String): LoginState()
}