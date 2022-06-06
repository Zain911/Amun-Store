package com.example.amunstore.ui.auth

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.amunstore.data.model.details.ProductDetailsResponse
import com.example.amunstore.data.model.user.User
import com.example.amunstore.data.repositories.user.UserRepository

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
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


     fun getUserByEmail(email:String) {
        CoroutineScope(Dispatchers.IO).launch {
            val response =repository.getUserByEmail("email:$email")

            withContext(Dispatchers.Main) {


                if (response.isSuccessful) {
                if (response.body()?.customers.isNullOrEmpty())
                {
//didn't find any emails
                    users.postValue(false)
                }
                else{
                    response.body()
//found the email
                    users.postValue(true)}

                }

                    //    loading.value = false
                }



            }
        }
    }
//    fun insertUser(user: User){
//        repository.insertUser(user)
//    }

