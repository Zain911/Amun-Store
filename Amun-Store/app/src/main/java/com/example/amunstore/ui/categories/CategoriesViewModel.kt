package com.example.amunstore.ui.categories

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.amunstore.model.Products
import com.example.amunstore.repository.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(private val repo: ProductsRepository) : ViewModel() {

    //Todo add suspend function to call get all products
    val productList = MutableLiveData<List<Products>?>()

    suspend fun getAllProducts() {
        productList.postValue(repo.getAllProducts().body()?.products)
    }


}