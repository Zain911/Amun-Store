package com.example.amunstore.ui.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.amunstore.model.details.ProductDetailsResponse
import com.example.amunstore.repository.products.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel@Inject constructor(val repository: ProductsRepository) : ViewModel() {

   // val productDetails = MutableLiveData<ProductDetailsResponse>()

//    suspend fun getProductDetails(byID : Long) =
//        productDetails.postValue(//repository.getBrands().body()?.brands
//            repository.getProductsByID(byId = byID).body()
//        )


    val errorMessage = MutableLiveData<String>()
    val productDetails = MutableLiveData<ProductDetailsResponse>()

    var job: Job? = null

   // val loading = MutableLiveData<Boolean>()

    fun getProductDetails(id:Long) {
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
        //loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }


}