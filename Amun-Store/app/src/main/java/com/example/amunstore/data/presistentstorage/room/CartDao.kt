package com.example.amunstore.data.presistentstorage.room

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.example.amunstore.data.model.cart.ItemCart


@Dao
interface CartDao {
    @Insert(onConflict = REPLACE)
    fun addToCart(itemCart: ItemCart)

    @Query("Select * From ItemCart" )
     fun getCartProducts() :LiveData<List<ItemCart>>

    @Delete
    fun deleteItem(itemCart: ItemCart)


}