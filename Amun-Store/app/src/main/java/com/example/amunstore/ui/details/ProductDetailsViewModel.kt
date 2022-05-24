package com.example.amunstore.ui.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.amunstore.model.details.ProductDetailsResponse
import com.example.amunstore.model.product.ProductsResponse
import com.example.amunstore.repository.details.ProductDetailsRepository
import com.example.amunstore.repository.vendor.BrandsRepository
import com.example.example.CustomCollections
import com.example.example.SmartCollections
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel@Inject constructor(val repository: ProductDetailsRepository) : ViewModel() {

    val productDetails = MutableLiveData<ProductDetailsResponse>()

    suspend fun getProductDetails(byID : String) =
        productDetails.postValue(//repository.getBrands().body()?.brands
            repository.getProductsByID(byId = byID).body()
        )


}