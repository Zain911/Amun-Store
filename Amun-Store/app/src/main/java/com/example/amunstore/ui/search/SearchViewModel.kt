package com.example.amunstore.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.amunstore.model.product.Product
import com.example.amunstore.repository.products.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(val repo: ProductsRepository) : ViewModel() {
    private val _productsList = MutableLiveData<List<Product>>()

    suspend fun getProduct() {
        _productsList.value = repo.getAllProducts().body()?.products
    }

    var productsList: LiveData<List<Product>> = _productsList

    val filterList = MutableLiveData<List<Product>?>()

    fun searchProduct(query: String, productFilter: String) {
        query.let {
            if (it == "")
                filterList.postValue(arrayListOf())
            else {
                filterList.postValue(productsList.value?.filter {
                    it.title?.contains(query, true) ?: false

                })

            }
        }


    }

    fun sortProducts(sortby: String) {
        if (filterList.value != null) {
            if (sortby == "price: High to Low")
                filterList.postValue(filterList.value?.sortedByDescending {
                    it.variants[0].price
                })
            else {
                filterList.postValue(filterList.value?.sortedBy {
                    when (sortby) {
                        "Alphabetically" -> it.title
                        "price: Low to High" -> it.variants[0].price

                        else -> {
                            it.title
                        }
                    }

                })
            }
        }

    }
}


