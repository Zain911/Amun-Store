package com.example.amunstore.data.repositories.cart

import androidx.lifecycle.LiveData
import com.example.amunstore.data.model.cart.ItemCart


interface CartRepositoryInterface {
    suspend fun getAllItemCart(): LiveData<List<ItemCart>>
    suspend fun addItem(itemCart: ItemCart)
    fun deleteItem(itemCart: ItemCart)
    fun updateItem(itemCart: ItemCart)

    fun getCartItemsCount(): LiveData<Int>
}