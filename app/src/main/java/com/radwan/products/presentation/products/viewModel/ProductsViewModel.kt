package com.radwan.products.presentation.companies.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.radwan.products.domain.model.ProductListing
import com.radwan.products.domain.repository.ProductsRepository
import com.radwan.products.presentation.products.event.ProductsEvent
import com.radwan.products.presentation.products.state.ProductsState
import com.radwan.products.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val companiesRepository: ProductsRepository
) : ViewModel() {

    private val _navigateToProductDetails = MutableSharedFlow<ProductListing>()
    val navigateToProductDetails = _navigateToProductDetails.asSharedFlow()

    private val _error = MutableSharedFlow<String>()
    val error = _error.asSharedFlow()

    var state by mutableStateOf(ProductsState())

    init {
        getProducts()
    }

    fun onEvent(event: ProductsEvent) = viewModelScope.launch {
        when (event) {
            is ProductsEvent.Refresh -> {
                getProducts()
            }

            is ProductsEvent.OnItemClicked -> {
                _navigateToProductDetails.emit(state.products[event.position])
            }

        }
    }



    private fun getProducts(
    ) {
        viewModelScope.launch {
            companiesRepository.getProducts()
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            state = state.copy(
                                products = result.data ?: emptyList(),isLoading = false
                            )
                        }

                        is Resource.Error -> {
                            state = state.copy(isLoading = false)
                            _error.emit(result.message ?: "Unknown error")
                        }

                        is Resource.Loading -> {
                            state = state.copy(isLoading = true)
                        }
                    }
                }
        }
    }
}