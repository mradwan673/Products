package com.radwan.products.presentation.productDetails.state

import com.radwan.products.domain.model.ProductListing

data class ProductDetailsState(
    val productInfo: ProductListing = ProductListing(),
    val isLoading: Boolean = false
)
