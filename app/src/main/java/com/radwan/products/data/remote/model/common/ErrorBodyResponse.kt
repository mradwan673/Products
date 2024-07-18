package com.radwan.products.data.remote.model.common

import com.google.gson.annotations.SerializedName

data class ErrorBodyResponse(
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String
)