package com.radwan.products.domain.repository

import com.radwan.products.data.remote.model.login.LoginResponse
import com.radwan.products.domain.model.ProductListing
import com.radwan.products.util.Resource
import kotlinx.coroutines.flow.Flow

interface ProductsRepository {


    suspend fun login(body: HashMap<String, Any>
    ): Flow<Resource<LoginResponse>>


    suspend fun getProducts(
    ): Flow<Resource<List<ProductListing>>>

}