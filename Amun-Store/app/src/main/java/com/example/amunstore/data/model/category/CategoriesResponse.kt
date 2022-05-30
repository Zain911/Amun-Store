package com.example.example

import com.google.gson.annotations.SerializedName


data class CategoriesResponse (

  @SerializedName("custom_collections" ) var customCollections : ArrayList<CustomCollections> = arrayListOf()

)