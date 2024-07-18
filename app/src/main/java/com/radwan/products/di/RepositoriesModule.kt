package com.radwan.products.di

import com.radwan.products.data.repository.ProductsRepositoryImpl
import com.radwan.products.domain.repository.ProductsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {


    @Binds
    @Singleton
    abstract fun bindStockRepository(
        productsRepositoryImpl: ProductsRepositoryImpl
    ): ProductsRepository
}