package com.example.amunstore.data.repositories.user

import com.example.amunstore.data.model.order.Order
import com.example.amunstore.data.model.user.User

interface UserRepositoryInterface {

    fun isUserLoggedIn(): Boolean

    fun getUserOrders() : List<Order>

    fun getUser() : User
}