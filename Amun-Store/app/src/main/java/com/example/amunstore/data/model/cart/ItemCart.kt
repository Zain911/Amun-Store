package com.example.amunstore.data.model.cart

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class ItemCart(
    @PrimaryKey
    @SerializedName("id") val id: Long,
    @SerializedName("title") val title: String?,
    @SerializedName("price") val price: String?,
    @SerializedName("src") val src: String?,
    @SerializedName("item_number") var item_number: Int?,
    @SerializedName("item_size") val size: String?
)