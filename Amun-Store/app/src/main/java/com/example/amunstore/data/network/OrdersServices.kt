package com.example.amunstore.data.network

import com.example.amunstore.data.model.order.Order
import com.example.amunstore.data.model.order.OrdersResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface OrdersServices {

    //https://mobile-ismailia.myshopify.com/admin/api/2022-04/orders.json?status=any&customer_id=6432302989541
    @GET("orders.json")
    suspend fun getUserOrders(
        @Query("status") status: String = "any",
        @Query("customer_id") customer_id: Long
    ): OrdersResponse

}