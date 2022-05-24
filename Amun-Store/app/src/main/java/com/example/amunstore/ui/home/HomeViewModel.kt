package com.example.amunstore.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.amunstore.repository.vendor.BrandsRepository
import com.example.example.SmartCollections
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(val repository: BrandsRepository) : ViewModel() {


    val brands = MutableLiveData<List<SmartCollections>>()

    suspend fun getBrands()=
        brands.postValue(repository.getBrands().body()?.brands)


}


