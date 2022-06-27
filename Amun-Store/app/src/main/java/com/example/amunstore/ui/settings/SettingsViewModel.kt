package com.example.amunstore.ui.settings

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.amunstore.data.model.product.Product
import com.example.amunstore.data.repositories.products.ProductsRepository
import com.example.amunstore.data.repositories.productvendor.ProductVendorRepository

import com.example.example.SmartCollections
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repository: ProductVendorRepository,
    private val repo: ProductsRepository,
) : ViewModel() {



}