package com.radwan.products.presentation.login.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.radwan.products.domain.repository.ProductsRepository
import com.radwan.products.presentation.login.event.LoginEvent
import com.radwan.products.presentation.login.state.LoginState
import com.radwan.products.util.Resource
import com.radwan.products.presentation.common.enums.Language
import com.radwan.products.presentation.common.extentions.getCurrentLocale
import com.radwan.products.presentation.common.extentions.setCurrentLocale
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val productsRepository: ProductsRepository
) : ViewModel() {

    private val _navigateToProducts = MutableSharedFlow<Unit>()
    val navigateToProducts = _navigateToProducts.asSharedFlow()

    private val _error = MutableSharedFlow<String>()
    val error = _error.asSharedFlow()

    var state by mutableStateOf(LoginState())



    fun onEvent(event: LoginEvent) = viewModelScope.launch {

        when (event) {
            is LoginEvent.OnLoginClicked -> {
                login(HashMap<String, Any>().apply {
                    put("username", state.userName)
                    put("password", state.password)
                })
            }

            is LoginEvent.OnUsernameChanged -> {
                state = state.copy(userName = event.username,isLoginBtnEnabled = state.userName.isNotEmpty() && state.password.isNotEmpty())
            }

            is LoginEvent.OnPasswordChanged -> {
                state = state.copy(password = event.password,isLoginBtnEnabled = state.userName.isNotEmpty() && state.password.isNotEmpty())
            }

        }
    }


     fun login(body: HashMap<String, Any>
    ) {
        viewModelScope.launch {
            productsRepository.login(body)
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            _navigateToProducts.emit(Unit)
                            state = state.copy(isLoading = false)
                        }

                        is Resource.Error -> {
                            _error.emit(result.message ?: "Unknown error")
                            state = state.copy(isLoading = false)
                        }

                        is Resource.Loading -> {
                            state = state.copy(isLoading = true)
                        }
                    }
                }
        }
    }
}