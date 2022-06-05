package com.example.amunstore.data.repositories.cart

import androidx.lifecycle.LiveData
import com.example.amunstore.data.model.cart.ItemCart
import com.example.amunstore.data.presistentstorage.room.CartDao
import javax.inject.Inject


class CartRepository @Inject constructor(private val cartDao: CartDao) : CartRepositoryInterface {
    override suspend fun getAllItemCart(): LiveData<List<ItemCart>> =
        cartDao.getCartProducts()

    override suspend fun addItem(itemCart: ItemCart) {
        cartDao.addToCart(itemCart)
    }

    override  fun deleteItem(itemCart: ItemCart) {
        cartDao.deleteItem(itemCart)
    }

}
