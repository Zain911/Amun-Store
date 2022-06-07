package com.example.amunstore.data.repositories.user



import com.example.amunstore.data.model.customer.Customer
import com.example.amunstore.data.model.customer.CustomerResponse
import com.example.amunstore.data.model.order.Order
import com.example.amunstore.data.model.user.User
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback


interface UserRepositoryInterface {

    suspend fun createCustomer(customer: Customer): Callback<Customer>?

    suspend fun getUserByEmail(email : String): retrofit2.Response<CustomerResponse?>

    fun isUserLoggedIn(): Boolean

    fun getUserOrders() : List<Order>

    fun getUser() : User
}