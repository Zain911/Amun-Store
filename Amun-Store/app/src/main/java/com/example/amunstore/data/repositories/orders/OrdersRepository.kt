package com.example.amunstore.data.repositories.orders

import com.example.amunstore.data.model.order.OrderResponse
import com.example.amunstore.data.network.NetworkServices
import okhttp3.RequestBody
import retrofit2.Response
import javax.inject.Inject

class OrdersRepository @Inject constructor(private val networkServices: NetworkServices) :
    OrdersRepositoryInterface {

    override suspend fun getUserOrders(customerId: Long) =
        networkServices.getUserOrders(customer_id = customerId)

    override suspend fun getOrderById(orderId: Long) =
        networkServices.getOrderById(orderId)

    override suspend fun createOrder(order: RequestBody): Response<OrderResponse> {
        return networkServices.createOrder(order)
    }
}