package com.example.amunstore.ui.auth

import androidx.lifecycle.ViewModel
import com.example.amunstore.repository.users.UsersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val repository: UsersRepository) : ViewModel() {

//    fun getUserByEmail(email:String): LiveData<List<User>>? {
//      return repository.getUserByEmail(email)
//    }

//    fun insertUser(user: User){
//        repository.insertUser(user)
//    }

}