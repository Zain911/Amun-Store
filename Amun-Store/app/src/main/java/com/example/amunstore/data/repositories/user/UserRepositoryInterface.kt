package com.example.amunstore.data.repositories.user

import com.example.amunstore.data.model.order.Order
import com.example.amunstore.data.model.user.User
import com.example.amunstore.data.model.address.Address
import com.example.amunstore.data.model.address.AddressResponse
import retrofit2.Response

interface UserRepositoryInterface {

    fun isUserLoggedIn(): Boolean

    fun getUserOrders(): List<Order>

    fun getUser(): User

    suspend fun getUserAddresses(customerId : Long): AddressResponse
}