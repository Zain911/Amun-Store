package com.example.amunstore.ui.categories.singlecategory

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.amunstore.model.product.Products
import com.example.amunstore.repository.products.ProductsRepository
import com.example.example.CustomCollections
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SingleCategoryFragmentViewModel @Inject constructor(private val repo: ProductsRepository) :
    ViewModel() {

    //Todo add suspend function to call get all products
    val productList = MutableLiveData<List<Products>?>()

    suspend fun getAllProducts() {
        productList.postValue(repo.getAllProducts().body()?.products)
    }

    suspend fun getProductByCategory(category: CustomCollections) {
        productList.postValue(repo.getProductByCategory(category).body()?.products)
    }

}