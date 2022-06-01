package com.example.amunstore.ui.favourites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.amunstore.data.model.product.Product
import com.example.amunstore.data.repositories.products.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(private val repo: ProductsRepository) : ViewModel() {

    var favList = MutableLiveData<List<Product>>()

    var data : LiveData<List<Product>> = favList

    suspend fun getFavourites() {
        favList.postValue(repo.getAllFavouriteProducts())
    }

    fun removeProductFromFavourite(product: Product) {
        repo.removeProductFromFavourite(product)
    }


}