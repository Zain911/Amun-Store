package com.example.amunstore.ui.cart

import androidx.lifecycle.*
import com.example.amunstore.data.model.cart.ItemCart
import com.example.amunstore.data.repositories.cart.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(private val cartRepository: CartRepository) : ViewModel() {

    var cartItems = MutableLiveData<List<ItemCart>>()

    var data: LiveData<List<ItemCart>> = cartItems

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
        cartRepository.updateItem(itemCart.apply { item_number = item_number?.plus(1) })
    }

    fun decreaseItemQuantity(itemCart: ItemCart) {
        cartRepository.updateItem(itemCart.apply {
            item_number = item_number?.minus(1)
        })
    }

    // increase item
    // decrease item

}