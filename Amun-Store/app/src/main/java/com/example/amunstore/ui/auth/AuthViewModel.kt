package com.example.amunstore.ui.auth

import android.app.Application
import android.content.Context
import android.content.res.Resources
import android.provider.Settings.Global.getString
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.amunstore.R
import com.example.amunstore.data.repositories.user.UserRepository
import dagger.hilt.android.internal.Contexts.getApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject
import java.util.regex.Pattern
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(private val repository: UserRepository, application:Application) : AndroidViewModel(application) {
    private val context
        get() = getApplication<Application>()

    val users = MutableLiveData<Boolean>()
    val isRegistered = MutableLiveData<Boolean>()
    val EMAIL_ADDRESS_PATTERN = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,32}" +  // 1 - 265
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,32}" +    // 0 - 64
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,32}" +   // 0 - 25
                ")+"
    )


    fun getUserByEmail(email: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getUserByEmail("email:$email")

            withContext(Dispatchers.Main) {


                if (response.isSuccessful) {

                    if ( response.body()!!.customers.size != 0) {

                        if (response.body()!!.customers[0].note.toString() .equals(password.toString() , true)  )
                        {
                            repository.addUserID(response.body()!!.customers[0].id)
                            users.postValue(true)

                            Log.v("TAG", response.body()!!.customers[0].note)
                            Log.v("TAG", password)

                        }
                        Log.v("TAG", response.body()!!.customers[0].note)
                        Log.v("TAG", password + password)
                    }

                    Toast.makeText(context,Resources.getSystem().getString(R.string.network_error),Toast.LENGTH_LONG).show()
                }


                //    loading.value = false
            }


        }
    }



    fun createUser(first_name:String , second_name :String, email :String, password:String , password_confirmation:String) {

        //creating json object
        val jsonObject = JSONObject()

        //creating json of customer
        val customerObject = JSONObject()
        customerObject.put("first_name", first_name)
        customerObject.put("last_name", second_name)
        customerObject.put("email", email)
        customerObject.put("password", password)
        customerObject.put("password_confirmation", password_confirmation)
        customerObject.put("note", password_confirmation)

        //add customer to main jsonObject
        jsonObject.put("customer" , customerObject)

        //convert this object to string
        val jsonObjectString = jsonObject.toString()
        Log.d("Pretty Printed JSON OBJECT:", jsonObjectString)

        // Create RequestBody ( We're not using any converter, like GsonConverter, MoshiConverter e.t.c, that's why we use RequestBody )
        // val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

        val body: RequestBody =
            RequestBody.create(MediaType.parse("application/json"), jsonObjectString)


                        CoroutineScope(Dispatchers.IO).launch {

            val response = repository.createCustomer(body)
            withContext(Dispatchers.Main) {
                if (response!!.isSuccessful) {

            isRegistered.postValue(  true)
                    // Convert raw JSON to pretty JSON using GSON library
//                    val gson = GsonBuilder().setPrettyPrinting().create()
//                    val prettyJson = gson.toJson(
//                        JsonParser.parseString(
//                            response?.body()
//                                ?.toString() // About this thread blocking annotation : https://github.com/square/retrofit/issues/3255
//                        )
//                    )

//                    Log.d("Pretty Printed JSON :", prettyJson)

                } else {
                    isRegistered.postValue(  false)

                }

                //adding the shared preferences ids

            }
        }
    }



    fun InputsIsEmpty(email:String , pass1:String , pass2: String) :Boolean {
        return   !(pass1.isNullOrEmpty() || pass2.isNullOrEmpty() || email.isNullOrEmpty() )

    }
    fun isPasswordConfirmed(pass1:String , pass2: String):Boolean{
        return pass1.equals(pass2)
    }

    fun checkIfPaswwordIsGood(pass1:String ):Boolean = pass1.count() > 5



    fun checkIfEmailIsGood(email: String):Boolean {
        email.lowercase()
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches()

    }

}
//    fun insertUser(user: User){
//        repository.insertUser(user)
//    }

