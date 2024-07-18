package com.radwan.products.data.mapper

import com.radwan.products.domain.model.ProductListing
import java.net.URLEncoder


fun ProductListing.toProductListing(): ProductListing {
    return ProductListing(
        id = id,
        title = title ,
        description = description ,
        brand = brand,
        category = category,
        price = price,
        rating = price,
        thumbnail = URLEncoder.encode(thumbnail, "UTF-8")
    )
}