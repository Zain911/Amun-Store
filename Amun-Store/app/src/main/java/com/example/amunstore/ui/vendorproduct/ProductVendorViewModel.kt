package com.example.amunstore.ui.vendorproduct

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.amunstore.data.model.product.Product
import com.example.amunstore.data.repositories.products.ProductsRepository
import com.example.amunstore.data.repositories.productvendor.ProductVendorRepository

import com.example.example.SmartCollections
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductVendorViewModel @Inject constructor(
    private val repository: ProductVendorRepository,
    private val repo: ProductsRepository,
) : ViewModel() {

    val brands = MutableLiveData<List<SmartCollections>>()
    val products = MutableLiveData<List<Product>>()

    suspend fun getProducts(vendorID: String) =
        products.postValue(repository.getProductsVendor(vendorID).body()?.products)


    fun addItemToFavourite(product: Product) {
        repo.addProductToFavourite(product)
    }

    fun removeItemFromFavourite(product: Product) {
        repo.removeProductFromFavourite(product)
    }
}