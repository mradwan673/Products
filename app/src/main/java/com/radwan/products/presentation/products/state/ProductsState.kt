package com.radwan.products.presentation.products.state

import com.radwan.products.domain.model.ProductListing

data class ProductsState(
    val products: List<ProductListing> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
)
