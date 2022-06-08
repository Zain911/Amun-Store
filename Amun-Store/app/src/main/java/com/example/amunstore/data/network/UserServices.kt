package com.example.amunstore.data.network

import com.example.amunstore.data.model.address.AddAddress
import com.example.amunstore.data.model.address.AddingAddressResponseModel
import com.example.amunstore.data.model.address.AddressResponse
import com.example.amunstore.data.model.customer.CustomerResponse
import com.example.amunstore.data.model.order.Customer
import okhttp3.RequestBody
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

    //    https://c48655414af1ada2cd256a6b5ee391be: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@mobile-ismailia.myshopify.com/admin/api/2022-04/customers/search.json?query=email:hihi@gmail.com
    @GET("customers/search.json")
    suspend fun getUserByEmail(
        @Query(value = "query") emails: String
    ): Response<CustomerResponse?>

    //https://your-development-store.myshopify.com/admin/api/2022-07/customers.json
    @POST("customers.json")
    suspend fun createCustomer(@Body body: RequestBody): Response<Customer>
}