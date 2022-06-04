package com.example.amunstore.ui.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.amunstore.data.model.details.ProductDetailsResponse
import com.example.amunstore.data.repositories.products.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(val repository: ProductsRepository) :
    ViewModel() {

    val errorMessage = MutableLiveData<String>()
    val productDetails = MutableLiveData<ProductDetailsResponse>()
    val sizeChooser = MutableLiveData<Int>()
    var job: Job? = null

    fun getProductDetails(id: Long) {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getProductsByID(id)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    productDetails.postValue(
                        response.body())
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

    fun setVarientsPosition(position:Int){
        sizeChooser.postValue(
            position)
    }


}