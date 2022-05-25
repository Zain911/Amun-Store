package com.example.amunstore.model.details

import com.example.amunstore.model.product.Products
import com.google.gson.annotations.SerializedName

data class ProductDetailsResponse(
    @SerializedName("product" ) var products : Products = Products()
)