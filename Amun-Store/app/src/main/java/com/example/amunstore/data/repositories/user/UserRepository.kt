package com.example.amunstore.data.repositories.user

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import android.content.res.Resources.getSystem
import com.example.amunstore.R
import com.example.amunstore.data.model.customer.Customer
import com.example.amunstore.data.model.customer.CustomerResponse
import com.example.amunstore.data.model.user.User
import com.example.amunstore.data.network.NetworkServices
import okhttp3.RequestBody
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val networkServices: NetworkServices,
    private val application: Application
) : UserRepositoryInterface {

    //shared preference in repo
    val sharedpreferences: SharedPreferences = application.applicationContext.getSharedPreferences(
        application.applicationContext.resources.getString(R.string.preference_loggin),
        Context.MODE_PRIVATE
    )
    val pref_val =application.applicationContext.resources.getString(R.string.preference_loggin)


    override suspend fun createCustomer(customer: RequestBody): Response<Customer>? {

        return  networkServices.createCustomer(customer)

    }

    override suspend fun getUserByEmail(email: String): Response<CustomerResponse?> {
        return networkServices.getUserByEmail(email)
    }

    override fun isUserLoggedIn(): Boolean {
        //shared preference in repo

        if (sharedpreferences.getLong(pref_val,0) != 0L )
    { //logged in user
        return true  }


//       not logged in user
        return false
    }

    override fun getUserOrders() = networkServices.getUserOrders()

    override fun addUserID(id : Long) {
        sharedpreferences.edit().putLong( pref_val , id ).apply()
    }



    override fun getUser(): User {
        TODO("Implement the return of userID based on room or shared prefs")

        //return of user id in LONG format form shared preferences
        //just uncomment the following
        /*            return sharedpreferences.getLong(pref_val,0)      */
    }
}