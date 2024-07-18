package com.radwan.products.presentation.common.extentions

import com.radwan.products.data.remote.model.common.ErrorResponse
import com.google.gson.Gson
import retrofit2.HttpException


fun Throwable.getErrorMessageFormGson(): String {
    try {
        val json :String = (this as HttpException).response()?.errorBody()?.string()?.trimIndent() ?: "{\"message\": \"An Error Occurred\"}"
        return  Gson().fromJson(json, ErrorResponse::class.java).message
    }catch (e:Exception){
        return "An Error Occurred"
    }
}

fun Throwable.getErrorsArrayFormGson():String{
    try {
        val json :String = (this as HttpException).response()?.errorBody()?.string()?.trimIndent() ?: "{\"message\": \"An Error Occurred\"}"
        return  json
    }catch (e:Exception){
        val defaultMessage = mutableMapOf<String, List<String>>()
        defaultMessage["errors"] = listOf("An Error Occurred")
        return "An Error Occurred"
    }
}