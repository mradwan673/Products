package com.radwan.products.data.repository

import com.radwan.products.data.remote.ProductsApi
import com.radwan.products.data.remote.model.common.ErrorBodyResponse
import com.radwan.products.data.remote.model.login.LoginResponse
import com.radwan.products.domain.model.ProductListing
import com.radwan.products.domain.repository.ProductsRepository
import com.radwan.products.util.Resource
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductsRepositoryImpl @Inject constructor(
    private val api: ProductsApi,
) : ProductsRepository {



    override suspend fun login(body: HashMap<String, Any>): Flow<Resource<LoginResponse>> {
        return flow {
            emit(Resource.Loading(true))

            try {
                  emit(Resource.Success(api.login(body)))
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error(getBackendErrorMessage(e.response()?.errorBody())?:"Couldn't load data"))
                null
            }
        }
}

    override suspend fun getProducts( ): Flow<Resource<List<ProductListing>>> {
        return flow {
            emit(Resource.Loading(true))

          try {
                val response = api.getProducts()
                response.products?.let {  emit(Resource.Success(it))}
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
              emit(Resource.Error(getBackendErrorMessage(e.response()?.errorBody())?:"Couldn't load data"))
                null
            }
        }
    }



    private fun getBackendErrorMessage(responseBody: ResponseBody?): String? {
        val gson = Gson()
        val type = object : TypeToken<ErrorBodyResponse>() {}.type
        val errorResponse: ErrorBodyResponse? = gson.fromJson(responseBody?.charStream(), type)

        return errorResponse?.message
    }
}