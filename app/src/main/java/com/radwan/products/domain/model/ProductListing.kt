package com.radwan.products.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class ProductListing(
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val brand: String = "",
    val category: String = "",
    val price: Double = 0.0,
    val rating: Double = 0.0,
    val thumbnail: String = "",
) : Parcelable
