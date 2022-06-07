package com.example.amunstore.data.network

import com.example.amunstore.data.model.address.AddAddress
import com.example.amunstore.data.model.address.AddingAddressResponseModel
import com.example.amunstore.data.model.address.Address
import com.example.amunstore.data.model.address.AddressResponse
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface UserServices {


    //admin/api/2022-04/customers/207119551/addresses.json
    @GET("customers/{customer_id}/addresses.json")
    suspend fun getUserAddresses(@Path("customer_id") customerId: Long): AddressResponse

    //   @Headers("Content-Type: application/json")

    @POST("customers/{customer_id}/addresses.json")
    suspend fun addUserAddress(
        @Path("customer_id") customerId: String,
        @Body address: AddAddress,
    ): Response<AddingAddressResponseModel>
//    suspend fun addUserAddress(@Path("customer_id") customerId: Long, @Body address: String): Response<AddingAddressResponseModel>


}