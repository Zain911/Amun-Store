package com.example.amunstore.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.amunstore.data.model.order.Order
import com.example.amunstore.data.model.product.Product
import com.example.amunstore.data.repositories.products.ProductsRepository
import com.example.amunstore.data.repositories.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val productRepository: ProductsRepository,
    private val userRepository: UserRepository
) :
    ViewModel() {

    val userName = MutableLiveData<String?>()

    val favProductList = MutableLiveData<List<Product>>()
    val ordersList = MutableLiveData<List<Order>>()
    val isUserLoggedIn = MutableLiveData<Boolean>()

    fun isUserLoggedIn() {
        val isLoggedIn = userRepository.isUserLoggedIn()
        isUserLoggedIn.postValue(isLoggedIn)
        if (isLoggedIn) {
            //Todo change it to user name saved in room or shared prefs
            val user = userRepository.getUser()
            user.name.let {
                userName.postValue(user.name)
            }

        } else {
            userName.postValue("Guest")
        }
    }

    suspend fun getUserFavouriteProducts() {
        favProductList.postValue(productRepository.getAllFavouriteProducts())
    }

    fun getUserOrders() {

        userRepository.getUserOrders()
    }


}