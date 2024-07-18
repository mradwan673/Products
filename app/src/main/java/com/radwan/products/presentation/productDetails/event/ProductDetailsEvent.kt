package com.radwan.products.presentation.productDetails.event

sealed class ProductDetailsEvent {
    data object OnBackButtonClicked : ProductDetailsEvent()
}