package com.example.amunstore.model.Brands

import com.example.example.SmartCollections
import com.google.gson.annotations.SerializedName

class BrandResponse {

    @SerializedName("smart_collections" ) var brands : List<SmartCollections> = arrayListOf()
}