package com.example.amunstore.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.amunstore.data.repositories.cart.CartRepository
import com.example.amunstore.data.repositories.products.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val favouriteRepository: ProductsRepository,
    private val cartRepository: CartRepository
) : ViewModel() {

    var cartItemsCount = MutableLiveData<Int>()
    var favouriteItemsCount = MutableLiveData<Int>()


    private val cartItemsObserver = Observer<Int> {
        cartItemsCount.postValue(it)
    }

    private val favouriteItemsObserver = Observer<Int> {
        favouriteItemsCount.postValue(it)
    }

    fun getCartItemsCount() {
        cartRepository.getCartItemsCount().observeForever(cartItemsObserver)
    }

    fun getFavouriteItemsCount() {
        favouriteRepository.getFavouritesItemCount().observeForever(favouriteItemsObserver)
    }

    override fun onCleared() {
        super.onCleared()
        cartRepository.getCartItemsCount().removeObserver(cartItemsObserver)
        favouriteRepository.getFavouritesItemCount().removeObserver(favouriteItemsObserver)
    }


}