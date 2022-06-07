package com.example.amunstore.data.network

import com.example.amunstore.data.model.order.Order
import com.example.amunstore.data.model.address.Address
import com.example.amunstore.data.model.address.AddressResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface UserServices {



    //admin/api/2022-04/customers/207119551/addresses.json
    @GET("customers/{customer_id}/addresses.json")
    suspend fun getUserAddresses(@Path("customer_id") customerId: Long): AddressResponse

}