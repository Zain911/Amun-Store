package com.example.amunstore.data.repositories.user

import com.example.amunstore.data.model.address.AddAddressRequestModel
import com.example.amunstore.data.model.address.AddressResponse
import com.example.amunstore.data.model.customer.CustomerResponse
import com.example.amunstore.data.model.order.Customer
import com.example.amunstore.data.model.user.User
import okhttp3.RequestBody
import retrofit2.Response

interface UserRepositoryInterface {

    suspend fun addUserAddress(address: AddAddressRequestModel)

    fun isUserLoggedIn(): Boolean

    fun getUser(): User

    suspend fun getUserAddresses(customerId: Long): AddressResponse

    fun getCustomerId(): Long

    fun setCustomerId(customerId: Long)

    fun getUserName(): String

    fun setUserName(name: String)

    suspend fun createCustomer(customer: RequestBody): Response<Customer>?

    suspend fun getUserByEmail(email: String): Response<CustomerResponse?>

    suspend fun getUserEmailById(id: Long): Response<Customer>

    fun getUserEmail() : String

}