package com.radwan.products.presentation.login.event

import android.content.Context

sealed class LoginEvent {
    data class OnUsernameChanged (val username: String) : LoginEvent()
    data class OnPasswordChanged (val password: String) : LoginEvent()
    data object OnLoginClicked : LoginEvent()
}