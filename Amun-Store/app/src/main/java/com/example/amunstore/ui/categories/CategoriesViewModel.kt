package com.example.amunstore.ui.categories

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.amunstore.model.Products

class CategoriesViewModel : ViewModel() {

    var productList = MutableLiveData<List<Products>>()
}