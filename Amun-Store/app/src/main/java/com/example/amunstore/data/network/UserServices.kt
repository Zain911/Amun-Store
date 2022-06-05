package com.example.amunstore.data.network

import com.example.amunstore.data.model.order.Order

interface UserServices {

    //TODO make it get data from the shopify API
    fun getUserOrders(): List<Order>
}