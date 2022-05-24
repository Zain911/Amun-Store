package com.example.amunstore.model

import com.google.gson.annotations.SerializedName

data class ProductDetailsResponse(
    @SerializedName("product")  val product: Product
)