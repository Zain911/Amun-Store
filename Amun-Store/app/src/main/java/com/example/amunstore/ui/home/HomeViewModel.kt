package com.example.amunstore.ui.home

import android.os.Bundle
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.example.amunstore.R
import com.example.amunstore.repository.vendor.BrandsRepository
import com.example.example.SmartCollections
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(val repository: BrandsRepository) : ViewModel() {


    fun NavigateToProductVendor(it: SmartCollections, view: View) {
        val nav = Navigation.findNavController(view)
        val bundle = Bundle()
       // bundle.putSerializable(ARTICLE_KEY, it)
        nav.navigate(R.id.action_navigation_home_to_productVendor)
    }


    val brands = MutableLiveData<List<SmartCollections>>()

    suspend fun getBrands() =
        brands.postValue(repository.getBrands().body()?.brands)


}


