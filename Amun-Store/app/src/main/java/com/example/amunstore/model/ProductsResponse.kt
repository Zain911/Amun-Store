package com.example.amunstore.model

import com.google.gson.annotations.SerializedName


data class ProductsResponse (
  @SerializedName("products" ) var products : ArrayList<Products> = arrayListOf()
)