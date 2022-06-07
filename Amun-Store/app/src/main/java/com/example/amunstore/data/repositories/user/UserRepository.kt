package com.example.amunstore.data.repositories.user

import android.util.Log
import com.example.amunstore.data.model.address.AddAddress
import com.example.amunstore.data.model.address.Address
import com.example.amunstore.data.model.user.User
import com.example.amunstore.data.network.NetworkServices
import com.example.amunstore.data.network.UserServices
import com.example.amunstore.data.presistentstorage.sharedprefs.UserSharedPreferences
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val networkServices: NetworkServices,
    private var sharedPref: UserSharedPreferences,
) : UserRepositoryInterface {

    override suspend fun addUserAddress(address: AddAddress) {


        val response = networkServices.addUserAddress("6466883977445", address)
        try {
            if (response.isSuccessful) {
           //     return "Done"

            } else {
                val jObjError =
                    JSONObject(response.errorBody()!!.toString()).getJSONObject("errors")
              //  return "Check Your Input"
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }


    }

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