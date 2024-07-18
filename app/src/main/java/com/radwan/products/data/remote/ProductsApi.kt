package com.radwan.products.data.remote

import com.radwan.products.data.remote.model.login.LoginResponse
import com.radwan.products.data.remote.model.products.ProductsResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ProductsApi {

    @POST("/auth/login")
    suspend fun login(@Body body: HashMap<String, Any>): LoginResponse

    @GET("/products")
    suspend fun getProducts(): ProductsResponse

    companion object {
        const val BASE_URL = "https://dummyjson.com"
    }
}