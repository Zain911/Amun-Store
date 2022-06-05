package com.example.amunstore.ui.cart

import androidx.lifecycle.*
import com.example.amunstore.data.model.cart.ItemCart
import com.example.amunstore.data.repositories.cart.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(val cartRepository: CartRepository) : ViewModel() {

    var cartItems = MutableLiveData<List<ItemCart>>()

    //  var favList = MutableLiveData<List<Product>>()
    var data: LiveData<List<ItemCart>> = cartItems

    val x = Observer<List<ItemCart>> {
        cartItems.postValue(it)
    }


    suspend fun getCartItems() {
        cartRepository.getAllItemCart().observeForever(x)
        //cartItems.postValue(cartRepository.getAllItemCart())
    }


    override fun onCleared() {
        super.onCleared()
        cartItems.removeObserver(x)
    }

     fun removeItem(itemCart: ItemCart) {
        cartRepository.deleteItem(itemCart)

    }
}