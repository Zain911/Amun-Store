package com.example.amunstore.model.product

import com.google.gson.annotations.SerializedName


data class ProductsResponse (
  @SerializedName("products" ) var products : ArrayList<Product> = arrayListOf()
)