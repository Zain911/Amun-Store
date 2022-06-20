package com.example.amunstore.data.model.product

import com.google.gson.annotations.SerializedName

data class ProductImagesResponse(
    @SerializedName("images") var imagesList: ArrayList<Images> = arrayListOf()
)
