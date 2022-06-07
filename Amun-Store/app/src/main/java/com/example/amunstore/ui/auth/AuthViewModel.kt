package com.example.amunstore.ui.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.amunstore.data.repositories.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject
import java.util.regex.Pattern
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: UserRepository,
) : ViewModel() {
    val users = MutableLiveData<Boolean>()
    val isRegistered = MutableLiveData<Boolean>()
    val email_address_pattern: Pattern by lazy {
        Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,32}" +  // 1 - 265
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,32}" +    // 0 - 64
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,32}" +   // 0 - 25
                    ")+"
        )
    }

    fun getUserByEmail(email: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getUserByEmail("email:$email")
            withContext(Dispatchers.Main) {
                if (response.isSuccessful && response.body()!!.customers.isNotEmpty()) {
                    if (response.body()!!.customers[0].note.equals(password, true)) {

                        val userName =
                            "${response.body()!!.customers[0].firstName} ${response.body()!!.customers[0].lastName}"
                        repository.setCustomerId(response.body()!!.customers[0].id!!)
                        repository.setUserName(userName)
                        users.postValue(true)

                    } else {
                        users.postValue(false)
                    }
                } else {
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

    fun isPasswordConfirmed(pass1: String, pass2: String): Boolean = pass1 == pass2

    fun checkIfPaswwordIsGood(pass1: String): Boolean = pass1.count() > 5

    fun checkIfEmailIsGood(email: String): Boolean {
        email.lowercase()
        return email_address_pattern.matcher(email).matches()
    }

}


