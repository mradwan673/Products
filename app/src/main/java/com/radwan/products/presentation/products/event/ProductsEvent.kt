package com.radwan.products.presentation.products.event

sealed class ProductsEvent {
    data object Refresh: ProductsEvent()
    data class OnItemClicked(val position: Int): ProductsEvent()
}