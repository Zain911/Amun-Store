package com.example.amunstore.data.presistentstorage.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.amunstore.data.model.product.Product

@Dao
interface ProductsDao {

    @Query("SELECT * FROM product WHERE isFavourite = :isFavourite")
    fun getAllFavourite(isFavourite: Boolean = true): LiveData<List<Product>>

    @Query("SELECT * FROM product WHERE id IN (:productId)")
    fun loadAllByIds(productId: IntArray): List<Product>

    @Insert
    fun insertAll(vararg product: Product)

    @Delete
    fun delete(product: Product)

    @Insert(onConflict = REPLACE)
    fun addItemToFavourite(product: Product)

    @Query("SELECT * From Product WHERE (:productId)=id")
    fun getProductById(productId: Long): Product?

    @Query("SELECT COUNT(id) FROM product")
    fun getFavouritesItemsCount(): LiveData<Int>

    @Query("DELETE FROM Product")
    fun deleteAllData()

}