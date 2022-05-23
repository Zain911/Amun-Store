package com.example.amunstore.repository

import com.example.amunstore.network.ProductsServices

class ProductsRepository(
    private val weatherServices: ProductsServices,
    private val weatherDao: ProductDto
) : ProductsRepositoryInterface {


    override fun getMenCategoryProducts() {
        TODO("Not yet implemented")
    }

    override fun getWomanCategoryProducts() {
        TODO("Not yet implemented")
    }

    override fun getKidsCategoryProducts() {
        TODO("Not yet implemented")
    }

    override fun getSaleCategoryProducts() {
        TODO("Not yet implemented")
    }

    override suspend fun getAllProducts() {
        weatherServices.getProducts()
    }


}