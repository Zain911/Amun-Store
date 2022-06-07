package com.example.amunstore.ui.auth

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.amunstore.data.model.customer.Customer
import com.example.amunstore.data.repositories.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import okhttp3.MediaType
import okhttp3.RequestBody
import java.util.regex.Pattern
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(private val repository: UserRepository) : ViewModel() {

    val users = MutableLiveData<Boolean>()

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
                    if (!response.body()?.customers.isNullOrEmpty()) {
                        if (response.body()!!.customers[0].note.toString() == password)
                            users.postValue(true)
                    }
                    users.postValue(false)
                }

                //    loading.value = false
            }


        }
    }



    fun createUser(customer: Customer) :Boolean{
      CoroutineScope(Dispatchers.IO).launch {
          val body: RequestBody =
              RequestBody.create(MediaType.parse("application/json"), customer.toString())

         Log.v("TAGGG", body.toString())
            repository.createCustomer(customer)

            //creating user whatever
        }

        return true
    }

    fun InputsIsEmpty(email:String , pass1:String , pass2: String) :Boolean {
        return   !(pass1.isNullOrEmpty() || pass2.isNullOrEmpty() || email.isNullOrEmpty() )

    }
    fun isPasswordConfirmed(pass1:String , pass2: String):Boolean{
        return pass1.equals(pass2)
    }

    fun checkIfPaswwordIsGood(pass1:String , pass2:String):Boolean {


        return true
    }

    fun checkIfEmailIsGood(email: String):Boolean {
        email.lowercase()
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches()

    }

}
//    fun insertUser(user: User){
//        repository.insertUser(user)
//    }

