package com.example.amunstore.model.productvendors


import com.example.amunstore.model.product.Product
import com.google.gson.annotations.SerializedName

class ProductVendorResponse {
    @SerializedName("products" ) var products : ArrayList<Product> = arrayListOf()

}