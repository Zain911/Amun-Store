package com.example.amunstore.data.repositories.user

import com.example.amunstore.data.model.user.User
import com.example.amunstore.data.network.NetworkServices
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val networkServices: NetworkServices
) : UserRepositoryInterface {

    override fun isUserLoggedIn(): Boolean {

        //TODO change the value fo the return type based on the user logged in or just a guest
        return true
    }

    override fun getUserOrders() = networkServices.getUserOrders()

    override fun getUser(): User {
        TODO("Implement the return of user based on room or shared prefs")
    }
}