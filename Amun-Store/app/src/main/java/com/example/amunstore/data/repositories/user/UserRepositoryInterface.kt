package com.example.amunstore.data.repositories.user

import com.example.amunstore.data.model.address.AddressResponse
import com.example.amunstore.data.model.user.User

interface UserRepositoryInterface {

    fun isUserLoggedIn(): Boolean

    fun getUser(): User

    suspend fun getUserAddresses(customerId: Long): AddressResponse

    fun getCustomerId(): Long

    fun setCustomerId(customerId: Long)

    fun getUserName(): String

    fun setUserName(name: String)
}