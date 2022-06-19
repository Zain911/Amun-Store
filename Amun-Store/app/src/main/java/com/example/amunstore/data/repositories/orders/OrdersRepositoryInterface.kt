package com.example.amunstore.data.repositories.orders

import com.example.amunstore.data.model.order.OrderResponse
import com.example.amunstore.data.model.order.OrdersResponse
import okhttp3.RequestBody
import retrofit2.Response

interface OrdersRepositoryInterface {

    suspend fun getUserOrders(customerId: Long): OrdersResponse

    suspend fun getOrderById(orderId: Long): Response<OrderResponse>

    suspend fun createOrder(order: RequestBody): Response<OrderResponse>
}