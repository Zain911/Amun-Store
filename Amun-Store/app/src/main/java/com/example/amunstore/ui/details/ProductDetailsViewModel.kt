package com.example.amunstore.ui.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.amunstore.data.model.cart.ItemCart
import com.example.amunstore.data.model.details.ProductDetailsResponse
import com.example.amunstore.data.model.product.Product
import com.example.amunstore.data.repositories.cart.CartRepository
import com.example.amunstore.data.repositories.products.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val repository: ProductsRepository,
    private val repositoryCart: CartRepository
) :
    ViewModel() {

    val errorMessage = MutableLiveData<String>()
    val productDetails = MutableLiveData<ProductDetailsResponse>()
    val isInFavourite = MutableLiveData<Boolean>()
    var job: Job? = null

    fun getProductDetails(id: Long) {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getProductsByID(id)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    productDetails.postValue(
                        response.body()
                    )
                    //    loading.value = false
                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }

    }

    private fun onError(message: String) {
        errorMessage.value = message
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

    @Throws(ParseException::class)
    fun modifyDateLayout(inputDate: String): String? {
        val date: Date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").parse(inputDate) as Date
        return SimpleDateFormat("dd.MM.yyyy").format(date)
    }

    fun getCurrencyInfoForDefaultLocale(): String? {
        val defaultLocale = Locale.getDefault()
        val currency = Currency.getInstance(defaultLocale)
        return currency.symbol

    }

    suspend fun addToCart(itemCart: ItemCart) {
        repositoryCart.addItem(itemCart)
    }

    fun isProductInFavourite(productID: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            isInFavourite.postValue(repository.isProductFavourite(productID))
        }
    }

    fun addOrRemoveProductToFavourite(product: Product) {
        CoroutineScope(Dispatchers.IO).launch {
            if (isInFavourite.value == true) {
                product.isFavourite = false
                repository.removeProductFromFavourite(product)
                isInFavourite.postValue(false)
            } else {
                product.apply {
                    this.imageSrc = this.image?.src
                    isFavourite = true
                }
                repository.addProductToFavourite(product)
                isInFavourite.postValue(true)
            }
        }
    }

}