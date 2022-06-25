package com.example.amunstore.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.amunstore.data.model.cart.ItemCart
import com.example.amunstore.data.model.product.Product
import com.example.amunstore.data.repositories.cart.CartRepository
import com.example.amunstore.data.repositories.products.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val favouriteRepository: ProductsRepository,
    private val cartRepository: CartRepository,
) : ViewModel() {

    var cartItemsCount = MutableLiveData<Int>()
    var favouriteItemsCount = MutableLiveData<Int>()

    var cartItems = MutableLiveData<LiveData<Product>>()
    var favoriteItems = MutableLiveData<LiveData<Product>>()


    private val cartItemsCountObserver = Observer<Int> {
        cartItemsCount.postValue(it)
    }

    private val favouriteItemsCountObserver = Observer<Int> {
        favouriteItemsCount.postValue(it)
    }

    private val cartItemsObserver = Observer<List<ItemCart>> {
        // send data to api
        Log.d("TestCart", it.toString())
    }

    private val favoriteItemsObserver = Observer<List<Product>> {
        // send data to api
    }

    fun getCartItemsCount() {
        cartRepository.getCartItemsCount().observeForever(cartItemsCountObserver)
    }

    fun getFavouriteItemsCount() {
        favouriteRepository.getFavouritesItemCount().observeForever(favouriteItemsCountObserver)
    }

    suspend fun getFavoriteItems() {
        favouriteRepository.getAllFavouriteProducts().observeForever(favoriteItemsObserver)
    }

    suspend fun getCartItems() {
        cartRepository.getAllItemCart().observeForever(cartItemsObserver)
    }

    override fun onCleared() {
        super.onCleared()
        cartRepository.getCartItemsCount().removeObserver(cartItemsCountObserver)
        favouriteRepository.getFavouritesItemCount().removeObserver(favouriteItemsCountObserver)
    }


}