package com.example.amunstore.ui.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.amunstore.model.details.ProductDetailsResponse
import com.example.amunstore.repository.products.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel@Inject constructor(val repository: ProductsRepository) : ViewModel() {

    val productDetails = MutableLiveData<ProductDetailsResponse>()

    suspend fun getProductDetails(byID : String) =
        productDetails.postValue(//repository.getBrands().body()?.brands
            repository.getProductsByID(byId = byID).body()
        )


}