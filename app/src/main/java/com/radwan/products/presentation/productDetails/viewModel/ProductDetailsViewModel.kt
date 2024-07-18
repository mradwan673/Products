package com.radwan.products.presentation.productDetails.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.radwan.products.domain.repository.ProductsRepository
import com.radwan.products.presentation.common.navigation.Routes
import com.radwan.products.presentation.productDetails.event.ProductDetailsEvent
import com.radwan.products.presentation.productDetails.state.ProductDetailsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _product= savedStateHandle.toRoute<Routes.ProductsDetailsScreen>().product

    private val _error = MutableSharedFlow<String>()
    val error = _error.asSharedFlow()

    private val _popToProducts = MutableSharedFlow<Unit>()
    val popToProducts = _popToProducts.asSharedFlow()

    var state by mutableStateOf(ProductDetailsState())

    init {
        productDetails()
    }

    fun onEvent(event: ProductDetailsEvent) = viewModelScope.launch {
        when (event) {
            is ProductDetailsEvent.OnBackButtonClicked -> {
                _popToProducts.emit(Unit)
            }
        }
    }

    private fun productDetails() {
        viewModelScope.launch {

            state = state.copy(
                productInfo = _product,
                isLoading = false
            )

        }
    }

}