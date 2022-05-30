package com.example.amunstore.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.amunstore.model.product.Products
import com.example.amunstore.repository.products.ProductsRepository
import com.example.example.CustomCollections
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(val repo: ProductsRepository) : ViewModel() {
    private val _productsList = MutableLiveData<List<Products>>()

    suspend fun getProduct() {
        _productsList.value = repo.getAllProducts().body()?.products
    }

    var productsList: LiveData<List<Products>> = _productsList

    val filterList = MutableLiveData<List<Products>?>()

    fun searchProduct(query: String) {
        query.let {
            if (it == "")
                filterList.postValue(arrayListOf())
            else
                filterList.postValue(productsList.value?.filter {
                    it.title?.contains(query, true) ?: false
                })
        }
    }


}