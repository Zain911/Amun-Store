package com.example.amunstore.ui.settings

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.amunstore.data.model.product.Product
import com.example.amunstore.data.presistentstorage.sharedprefs.UserSharedPreferences
import com.example.amunstore.data.repositories.products.ProductsRepository
import com.example.amunstore.data.repositories.productvendor.ProductVendorRepository
import com.example.amunstore.data.repositories.user.UserRepository

import com.example.example.SmartCollections
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val sharedPreference: UserSharedPreferences
) : ViewModel() {

    fun isUserLoggedIn() = userRepository.isUserLoggedIn()

    fun clearSharedPreferences() {
        sharedPreference.clearAllCache()
    }

}