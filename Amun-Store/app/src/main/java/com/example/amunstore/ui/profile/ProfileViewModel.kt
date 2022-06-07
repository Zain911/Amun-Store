package com.example.amunstore.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.amunstore.data.model.order.Order
import com.example.amunstore.data.model.product.Product
import com.example.amunstore.data.presistentstorage.sharedprefs.UserSharedPreferences
import com.example.amunstore.data.repositories.orders.OrdersRepository
import com.example.amunstore.data.repositories.products.ProductsRepository
import com.example.amunstore.data.repositories.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val productRepository: ProductsRepository,
    private val userRepository: UserRepository,
    private val ordersRepository: OrdersRepository,
    private val sharedPreferences: UserSharedPreferences
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
            userName.postValue(sharedPreferences.getUserName())
        } else {
            userName.postValue("Guest")
        }
    }

    suspend fun getUserFavouriteProducts() {
        favProductList.postValue(productRepository.getAllFavouriteProducts())
    }

    suspend fun getUserOrders() {
        //TODO remove static customer id and get it from sharedPref
        //ordersList.postValue(ordersRepository.getUserOrders(sharedPreferences.getCustomerId()).orders)
        ordersList.postValue(ordersRepository.getUserOrders(6432302989541).orders)
    }


}