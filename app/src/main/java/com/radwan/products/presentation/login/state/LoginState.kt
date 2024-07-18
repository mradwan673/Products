package com.radwan.products.presentation.login.state


data class LoginState(
    val userName: String = "",
    val password: String = "",
    val isLoginBtnEnabled: Boolean = false,
    val isLoading: Boolean = false,
    val language: String  = "en",
    )
