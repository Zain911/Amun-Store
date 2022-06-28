package com.example.amunstore.ui.intro

import androidx.lifecycle.ViewModel
import com.example.amunstore.data.repositories.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class IntroViewModel @Inject constructor(
    private val repository: UserRepository,
) : ViewModel() {
    fun isUserLoggedIn(): Boolean = repository.isUserLoggedIn()
}