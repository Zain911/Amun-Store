package com.example.amunstore.data.repositories.user

import com.example.amunstore.data.model.customer.CustomerResponse
import com.example.amunstore.data.model.user.User
import com.example.amunstore.data.network.NetworkServices
import retrofit2.Response
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val networkServices: NetworkServices
) : UserRepositoryInterface {
    override suspend fun getUserByEmail(email: String): Response<CustomerResponse?> {
        return networkServices.getUserByEmail(email)
    }

    override fun isUserLoggedIn(): Boolean {

        //TODO change the value for the return type based on the user logged in or just a guest
        return true
    }

    override fun getUserOrders() = networkServices.getUserOrders()

    override fun getUser(): User {
        TODO("Implement the return of userID based on room or shared prefs")
    }
}