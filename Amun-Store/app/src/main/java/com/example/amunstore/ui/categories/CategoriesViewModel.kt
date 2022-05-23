package com.example.amunstore.ui.categories

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.amunstore.model.Products
import com.example.amunstore.repository.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(repo: ProductsRepository) : ViewModel() {

    var productList = repo.getAllProducts()
}