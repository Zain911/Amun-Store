package com.example.amunstore.ui.categories

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.amunstore.data.repositories.categories.CategoriesRepository
import com.example.example.CustomCollections
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(private val repo: CategoriesRepository) :
    ViewModel() {

    val categoriesList = MutableLiveData<List<CustomCollections>?>()

    suspend fun getCategories() {
        categoriesList.postValue(repo.getCategories().body()?.customCollections)
    }



}