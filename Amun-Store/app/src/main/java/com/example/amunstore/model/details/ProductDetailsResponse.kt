package com.example.amunstore.model.details

import com.example.amunstore.model.product.Product
import com.google.gson.annotations.SerializedName

data class ProductDetailsResponse(

    @SerializedName("product") var product : Product = Product()
)