package com.example.amunstore.ui.home

import android.text.Html
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.amunstore.R
import com.example.amunstore.model.getImage
import com.example.amunstore.repository.ProductsRepository
import com.example.amunstore.repository.vendor.BrandsRepositoryImp
import com.example.amunstore.repository.vendor.BrandsRepositoryInterface
import com.example.example.SmartCollections
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(val repository: BrandsRepositoryImp) : ViewModel() {

    val brands = MutableLiveData<List<SmartCollections>>()
    suspend fun getBrands()=
        brands.postValue(repository.getBrands().body()?.brands)

}


