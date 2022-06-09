package com.example.amunstore.data.repositories.orders

import com.example.amunstore.data.network.NetworkServices
import javax.inject.Inject

class OrdersRepository @Inject constructor(private val networkServices: NetworkServices) :
    OrdersRepositoryInterface {

    override suspend fun getUserOrders(customerId: Long) =
        networkServices.getUserOrders(customer_id = customerId)

    override suspend fun getOrderById(orderId: Long) =
        networkServices.getOrderById(orderId)

}