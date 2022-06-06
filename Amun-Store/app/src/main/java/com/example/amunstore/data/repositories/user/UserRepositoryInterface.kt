package com.example.amunstore.data.repositories.user


import androidx.lifecycle.LiveData
import com.example.amunstore.data.model.customer.CustomerResponse
import com.example.amunstore.data.model.order.Order
import com.example.amunstore.data.model.user.User

import okhttp3.Response

interface UserRepositoryInterface {

    suspend fun getUserByEmail(email : String): retrofit2.Response<CustomerResponse?>

    fun isUserLoggedIn(): Boolean

    fun getUserOrders() : List<Order>

    fun getUser() : User
}