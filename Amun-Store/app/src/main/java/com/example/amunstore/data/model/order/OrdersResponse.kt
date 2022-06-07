package com.example.amunstore.data.model.order

import com.google.gson.annotations.SerializedName

data class OrdersResponse (
    @SerializedName("orders" ) var orders : ArrayList<Order> = arrayListOf()
)