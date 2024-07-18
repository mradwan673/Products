package com.radwan.products.presentation.common.navigation

import com.radwan.products.domain.model.ProductListing
import kotlinx.serialization.Serializable

sealed class Routes {


    @Serializable
    object LoginScreen

    @Serializable
    object ProductsScreen

    @Serializable
    data class ProductsDetailsScreen(
        val product: ProductListing
    )


}