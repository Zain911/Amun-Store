package com.example.amunstore.data.model.order

import com.google.gson.annotations.SerializedName
import com.example.amunstore.data.model.order.Order
data class OrderResponse(
    @SerializedName("order") var order: Order? = null
)