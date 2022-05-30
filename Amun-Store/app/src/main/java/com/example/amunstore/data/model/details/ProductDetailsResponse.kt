package com.example.amunstore.data.model.details

import com.example.amunstore.data.model.product.Product
import com.google.gson.annotations.SerializedName

data class ProductDetailsResponse(

    @SerializedName("product") var product : Product = Product()
)