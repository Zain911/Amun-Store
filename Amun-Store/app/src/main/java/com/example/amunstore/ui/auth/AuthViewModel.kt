package com.example.amunstore.ui.auth

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.amunstore.data.repositories.user.UserRepository
import com.facebook.*
import com.facebook.messenger.BuildConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: UserRepository,
) : ViewModel() {
    val users = MutableLiveData<Boolean>()
    val isRegistered = MutableLiveData<Boolean>()

    fun getUserByEmail(email: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getUserByEmail("email:$email")
            withContext(Dispatchers.Main) {
                if (response.isSuccessful && response.body()!!.customers.isNotEmpty()) {
                    if (response.body()!!.customers[0].note.equals(password, false)) {

                        val userName =
                            "${response.body()!!.customers[0].firstName} ${response.body()!!.customers[0].lastName}"
                        repository.setCustomerId(response.body()!!.customers[0].id!!)
                        repository.setUserName(userName)
                        repository.setUserEmail(email)
                        repository.setFavouriteDraftOrderIdInSharedPrefs(
                            response.body()!!.customers[0].tags ?: ""
                        )
                        repository.setCartDraftOrderIdInSharedPrefs(
                            response.body()!!.customers[0].multipassIdentifier ?: ""
                        )
                        users.postValue(true)

                    } else {
                        //user is not exist
                        users.postValue(false)
                    }
                } else {
//                    response not successful
                    users.postValue(false)
                }
            }
        }
    }


    fun createUser(
        first_name: String,
        second_name: String,
        email: String,
        password: String,
        password_confirmation: String
    ) {

        //creating json object
        val jsonObject = JSONObject()

        //creating json of customer
        val customerObject = JSONObject()
        customerObject.put("first_name", first_name)
        customerObject.put("last_name", second_name)
        customerObject.put("email", email.lowercase())
        customerObject.put("password", password)
        customerObject.put("password_confirmation", password_confirmation)
        customerObject.put("note", password_confirmation)

        //add customer to main jsonObject
        jsonObject.put("customer", customerObject)

        //convert this object to string
        val jsonObjectString = jsonObject.toString()

        val body: RequestBody =
            RequestBody.create(MediaType.parse("application/json"), jsonObjectString)
        CoroutineScope(Dispatchers.IO).launch {

            val response = repository.createCustomer(body)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    isRegistered.postValue(true)
                } else {
                    isRegistered.postValue(false)
                }
            }
        }
    }


    fun inputsIsEmpty(email: String, pass1: String, pass2: String): Boolean =
        !(pass1.isEmpty() || pass2.isEmpty() || email.isEmpty())

    fun validatePasswordConfirmation(pass1: String, pass2: String): Boolean = pass1 == pass2

    fun validatePassword(pass1: String): Boolean = pass1.count() > 5

    fun validateEmail(email: String): Boolean {
        email.lowercase()
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isUserLoggedIn() =
        repository.isUserLoggedIn()

    fun setUserGuest() {
        repository.setUserLoggedIn(false)
    }

    @SuppressLint("LongLogTag")
    fun getUserProfile() {
        val token = AccessToken.getCurrentAccessToken()
        val userId = token?.userId
        val parameters = Bundle()
        parameters.putString(
            "fields",
            " first_name, last_name, name,  email"
        )

        GraphRequest(token,
            "/$userId/",
            parameters,
            HttpMethod.GET,
            { response ->
                runBlocking {
                    var facebookFirstName = ""
                    var facebookEmail = ""
                    var facebookLastName = ""
                    val jsonObject = response.jsonObject

                    // Facebook Access Token
                    // You can see Access Token only in Debug mode.
                    // You can't see it in Logcat using Log.d, Facebook did that to avoid leaking user's access token.
                    if (BuildConfig.DEBUG) {
                        FacebookSdk.setIsDebugEnabled(true)
                        FacebookSdk.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS)
                    }

                    // Facebook First Name
                    if (jsonObject!!.has("first_name")) {
                        facebookFirstName = jsonObject.getString("first_name")
                    }

                    // Facebook Last Name
                    if (jsonObject.has("last_name")) {
                        facebookLastName = jsonObject.getString("last_name")
                    }

                    // Facebook Email
                    if (jsonObject.has("email")) {
                        facebookEmail = jsonObject.getString("email")
                    }
                    createUser(
                        first_name = facebookFirstName,
                        second_name = facebookLastName,
                        email = facebookEmail,
                        password = "facebook",
                        password_confirmation = "facebook"
                    )
                    getUserByEmail(email = facebookEmail, password = "facebook")
                }
            }).executeAsync()
    }

    fun isLoggedInWithFacebook(): Boolean {
        val accessToken = AccessToken.getCurrentAccessToken()
        return accessToken != null && !accessToken.isExpired
    }

}


