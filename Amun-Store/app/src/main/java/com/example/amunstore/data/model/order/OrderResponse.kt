package com.example.amunstore.data.model.order

import com.google.gson.annotations.SerializedName

data class OrderResponse(
    @SerializedName("order") var order: Order? = null
)