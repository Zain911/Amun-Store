package com.example.amunstore.ui.categories.singlecategory

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.amunstore.model.product.Product
import com.example.amunstore.model.subcategory.SubCategory
import com.example.amunstore.repository.products.ProductsRepository
import com.example.example.CustomCollections
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SingleCategoryViewModel @Inject constructor(private val repo: ProductsRepository) :
    ViewModel() {

    //Todo add suspend function to call get all products
    private val productList = MutableLiveData<List<Product>?>()
    val viewedList = MutableLiveData<List<Product>?>()

    suspend fun getProductByCategory(category: CustomCollections) {
        productList.postValue(repo.getProductByCategory(category).body()?.products)
        viewedList.postValue(repo.getProductByCategory(category).body()?.products)
    }

    fun filterDataBasedOnSubCategory(category: SubCategory) {

        if (category.name == SubCategory.All.toString()) {
            viewedList.value = productList.value?.map { it.copy() }?.let { ArrayList(it) }
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

    fun addItemToFavourite(product: Product){
        repo.addProductToFavourite(product)
    }

    fun removeItemFromFavourite(product: Product){
        repo.removeProductFromFavourite(product)
    }


}