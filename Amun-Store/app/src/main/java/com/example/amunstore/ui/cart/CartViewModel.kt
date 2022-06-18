package com.example.amunstore.ui.cart

import androidx.lifecycle.*
import com.example.amunstore.data.model.cart.ItemCart
import com.example.amunstore.data.repositories.cart.CartRepository
import com.example.amunstore.data.repositories.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val cartRepository: CartRepository
) : ViewModel() {

    var cartItems = MutableLiveData<List<ItemCart>>()

    var data: LiveData<List<ItemCart>> = cartItems

    var userName = MutableLiveData<String>()
    var userAddress = MutableLiveData<String>()

    fun loadUserName() {
        userName.postValue(userRepository.getUserName())
    }

    private val x = Observer<List<ItemCart>> {
        cartItems.postValue(it)
    }

    suspend fun getCartItems() {
        cartRepository.getAllItemCart().observeForever(x)
    }

    override fun onCleared() {
        super.onCleared()
        cartItems.removeObserver(x)
    }

    fun removeItem(itemCart: ItemCart) {
        cartRepository.deleteItem(itemCart)
    }

    fun increaseItemQuantity(itemCart: ItemCart) {
        if ((itemCart.maxItem ?: 0) >= (itemCart.item_number ?: 0))
            cartRepository.updateItem(itemCart.apply { item_number = item_number?.plus(1) })
    }

    fun decreaseItemQuantity(itemCart: ItemCart) {
        if (itemCart.item_number !in 0..1)
            cartRepository.updateItem(itemCart.apply {
                item_number = item_number?.minus(1)
            })
    }

    suspend fun getUserAddresses() {
        val addresses = userRepository.getUserAddresses(userRepository.getCustomerId())
        val addressesFiltered = addresses.addresses.filter {
            it.default == true
        }
        if (addressesFiltered.isNotEmpty())
            userAddress.postValue(addressesFiltered[0].address1)
    }


}