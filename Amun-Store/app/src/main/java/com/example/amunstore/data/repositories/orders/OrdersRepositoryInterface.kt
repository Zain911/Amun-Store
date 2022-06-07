package com.example.amunstore.data.repositories.orders

import com.example.amunstore.data.model.order.Order
import com.example.amunstore.data.model.order.OrdersResponse

interface OrdersRepositoryInterface {

    suspend fun getUserOrders(customerId : Long) : OrdersResponse
}