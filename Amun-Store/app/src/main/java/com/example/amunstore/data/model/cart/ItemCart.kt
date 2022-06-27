package com.example.amunstore.data.model.cart

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class ItemCart(

    @SerializedName("id") val id: Long? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("price") val price: String? = null,
    @SerializedName("src") val src: String? = null,
    @SerializedName("item_number") var item_number: Int? = null,
    @SerializedName("item_size") val size: String? = null,
    @SerializedName("inventory_quantity") val maxItem: Int? = null,
    @PrimaryKey
    @SerializedName("variant_id") val variant_id: Long? = null,
)