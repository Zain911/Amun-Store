package com.example.amunstore.ui.address

import androidx.lifecycle.ViewModel
import com.example.amunstore.data.model.address.AddAddress
import com.example.amunstore.data.model.address.Address
import com.example.amunstore.data.repositories.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddressViewModel @Inject constructor(private val userRepository: UserRepository) :
    ViewModel() {
    suspend fun addNewAddress(address: AddAddress) =
        userRepository.addUserAddress(address)

}