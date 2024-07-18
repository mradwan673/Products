package com.radwan.products.data.remote.model.common

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("message") val message: String,
    @SerializedName("retry_after") val retryAfter: Int,
    @SerializedName("errors") val errors: Map<String, List<String>>
)