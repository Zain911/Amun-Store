package com.example.amunstore.data.repositories.products

import com.example.amunstore.data.model.details.ProductDetailsResponse
import com.example.amunstore.data.model.product.Product
import com.example.amunstore.data.model.product.ProductsResponse
import com.example.amunstore.data.network.NetworkServices
import com.example.amunstore.data.presistentstorage.room.ProductsDao
import com.example.example.CustomCollections
import retrofit2.Response
import javax.inject.Inject

class ProductsRepository @Inject constructor(
    private val productsDao: ProductsDao,
    private val networkServices: NetworkServices
) : ProductsRepositoryInterface {

    override suspend fun getProductByCategory(category: CustomCollections): Response<ProductsResponse> =
        networkServices.getProductsByCategory(category.id.toString())

    override suspend fun getAllProducts() =
        networkServices.getProducts()

    override suspend fun getProductsByID(byId: Long): Response<ProductDetailsResponse> =
        networkServices.getProductsByID(byId)


    override suspend fun getAllFavouriteProducts() = productsDao.getAllFavourite()

    override fun addProductToFavourite(product: Product) {
        productsDao.addItemToFavourite(product)
    }

    override fun removeProductFromFavourite(product: Product) {
        productsDao.delete(product)
    }

    override fun isProductFavourite(id: Long): Boolean {
        val product = productsDao.getProductById(id)
        product.let {
            if (it != null) {
                return it.isFavourite
            }
        }
        return false
    }


}