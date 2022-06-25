package com.example.amunstore.data.network

import com.example.amunstore.data.model.address.AddAddressRequestModel
import com.example.amunstore.data.model.address.AddingAddressResponseModel
import com.example.amunstore.data.model.address.AddressResponse
import com.example.amunstore.data.model.customer.*
import com.example.amunstore.data.model.order.Customer
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface UserServices {


    @GET("customers/{customer_id}/addresses.json")
    suspend fun getUserAddresses(@Path("customer_id") customerId: Long): AddressResponse

    @POST("customers/{customer_id}/addresses.json")
    suspend fun addUserAddress(
        @Path("customer_id") customer_id: String,
        @Body address: AddAddressRequestModel,
    ): Response<AddingAddressResponseModel?>

    @GET("customers/search.json")
    suspend fun getUserByEmail(
        @Query(value = "query") emails: String,
    ): Response<CustomerResponse?>

    @POST("customers.json")
    suspend fun createCustomer(@Body body: RequestBody): Response<Customer>

    @GET("customers/{customer_id}.json")
    suspend fun getUserEmailById(@Path("customer_id") customer_id: Long): Response<Customer>

    //6268209725698
    @PUT("customers/{customer_id}.json")
    suspend fun setCartDraftOrdersId(
        @Path("customer_id") customer_id: String,
        @Body customer: RequestCartDraftOrder,
    ): Response<CustomerSingleResponse>

    @PUT("customers/{customer_id}.json")
    suspend fun setFavouriteDraftOrdersId(
        @Path("customer_id") customer_id: String,
        @Body customer: RequestFavouriteDraftOrder,
    ): Response<CustomerSingleResponse>

}