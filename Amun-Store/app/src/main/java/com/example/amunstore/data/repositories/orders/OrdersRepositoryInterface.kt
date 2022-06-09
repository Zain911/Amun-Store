package com.example.amunstore.data.repositories.orders

import com.example.amunstore.data.model.order.Order
import com.example.amunstore.data.model.order.OrderResponse
import com.example.amunstore.data.model.order.OrdersResponse
import retrofit2.Response

interface OrdersRepositoryInterface {

    suspend fun getUserOrders(customerId: Long): OrdersResponse

    suspend fun getOrderById(orderId: Long): Response<OrderResponse>
}