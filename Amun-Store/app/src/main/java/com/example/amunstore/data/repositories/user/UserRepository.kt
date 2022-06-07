package com.example.amunstore.data.repositories.user

import com.example.amunstore.data.model.user.User
import com.example.amunstore.data.network.NetworkServices
import com.example.amunstore.data.presistentstorage.sharedprefs.UserSharedPreferences
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val networkServices: NetworkServices,
    private var sharedPref: UserSharedPreferences
) : UserRepositoryInterface {

    override fun isUserLoggedIn(): Boolean {
        return sharedPref.getCustomerId() == -1L
    }

    override fun getUser(): User {
        TODO("Implement the return of user based on room or shared prefs")
    }

    override suspend fun getUserAddresses(customerId: Long) =
        networkServices.getUserAddresses(customerId)

    override fun getCustomerId() =
        sharedPref.getCustomerId()

    override fun setCustomerId(customerId: Long) {
        sharedPref.setCustomerId(customerId)
    }

    override fun getUserName() =
        sharedPref.getUserName()

    override fun setUserName(name: String) {
        sharedPref.setUserName(name)
    }

}