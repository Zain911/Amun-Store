package com.example.amunstore.data.network


import com.example.amunstore.data.model.customer.CustomerResponse
import com.example.amunstore.data.model.order.Order
import com.example.amunstore.data.model.user.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UserServices {

//    https://c48655414af1ada2cd256a6b5ee391be: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@mobile-ismailia.myshopify.com/admin/api/2022-04/customers/search.json?query=email:hihi@gmail.com
    @GET("customers/search.json")
    suspend fun getUserByEmail(@Query(value = "query")  emails : String
    ): Response<CustomerResponse?>


    fun getUserOrders(): List<Order>
}