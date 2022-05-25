package com.example.amunstore.ui.categories.singlecategory

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.amunstore.model.product.Products
import com.example.amunstore.model.subcategory.SubCategory
import com.example.amunstore.repository.products.ProductsRepository
import com.example.example.CustomCollections
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SingleCategoryFragmentViewModel @Inject constructor(private val repo: ProductsRepository) :
    ViewModel() {

    //Todo add suspend function to call get all products
    private val productList = MutableLiveData<List<Products>?>()
    val viewedList = MutableLiveData<List<Products>?>()

    suspend fun getProductByCategory(category: CustomCollections) {
        productList.postValue(repo.getProductByCategory(category).body()?.products)
        viewedList.postValue(repo.getProductByCategory(category).body()?.products)
    }

    fun filterDataBasedOnSubCategory(category: SubCategory) {

        if (category.name == SubCategory.All.toString()) {
            viewedList.value = productList.value
        } else {
            viewedList.value = productList.value?.filter {
                it.productType == category.serverName
            }
        }
    }

    fun getSubCategories() = arrayListOf(
        SubCategory.All,
        SubCategory.TShirt,
        SubCategory.Accessories,
        SubCategory.Shoes,
        SubCategory.Jackets,
        SubCategory.Trousers,
        SubCategory.Suits,
        SubCategory.Skirts,
        SubCategory.Dresses,
        SubCategory.Jumpsuit,
        SubCategory.Leggings,
        SubCategory.Pyjamas,
        SubCategory.Tie,
        SubCategory.Shirts
    )


}