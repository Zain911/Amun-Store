package com.example.amunstore.data.repositories.orders

import com.example.amunstore.data.model.order.Order
import com.example.amunstore.data.network.NetworkServices
import javax.inject.Inject

class OrdersRepository @Inject constructor(private val networkServices: NetworkServices) :
    OrdersRepositoryInterface {

    override suspend fun getUserOrders(customerId: Long) =
        networkServices.getUserOrders(customer_id = customerId)

}