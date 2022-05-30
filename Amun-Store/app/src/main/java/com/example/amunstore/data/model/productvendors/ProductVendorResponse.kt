package com.example.amunstore.data.model.productvendors


import com.example.amunstore.data.model.product.Product
import com.google.gson.annotations.SerializedName

class ProductVendorResponse {
    @SerializedName("products" ) var products : ArrayList<Product> = arrayListOf()

}