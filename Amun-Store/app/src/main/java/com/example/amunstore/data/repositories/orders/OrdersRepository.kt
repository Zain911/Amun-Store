package com.example.amunstore.data.repositories.orders

import com.example.amunstore.data.model.order.AddOrderRequestModel
import com.example.amunstore.data.model.order.Order
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

    override suspend fun addUserOrder(addOrderRequestModel: RequestBody): Response<Order> =
        networkServices.addUserOrder(addOrderRequestModel)

    override suspend fun deleteOrder(orderId: Long) {
        networkServices.deleteOrder(orderId)
    }

}