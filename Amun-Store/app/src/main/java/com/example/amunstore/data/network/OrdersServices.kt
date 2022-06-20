package com.example.amunstore.data.network

import com.example.amunstore.data.model.order.AddOrderRequestModel
import com.example.amunstore.data.model.order.Order
import com.example.amunstore.data.model.order.OrderResponse
import com.example.amunstore.data.model.order.OrdersResponse
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface OrdersServices {

    //https://mobile-ismailia.myshopify.com/admin/api/2022-04/orders.json?status=any&customer_id=6432302989541
    @GET("orders.json")
    suspend fun getUserOrders(
        @Query("status") status: String = "any",
        @Query("customer_id") customer_id: Long,
    ): OrdersResponse

    @GET("orders/{order_id}.json")
    suspend fun getOrderById(@Path("order_id") orderId: Long): Response<OrderResponse>

    @POST("orders.json")
    suspend fun addUserOrder(@Body order: RequestBody): Response<Order>

    @DELETE("orders/{order_id}.json")
    suspend fun deleteOrder(@Path("order_id") order_id: Long)

}