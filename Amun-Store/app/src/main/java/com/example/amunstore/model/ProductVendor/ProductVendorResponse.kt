package com.example.amunstore.model.ProductVendor


import com.example.amunstore.model.product.Products
import com.google.gson.annotations.SerializedName

class ProductVendorResponse {
    @SerializedName("products" ) var products : ArrayList<Products> = arrayListOf()

}