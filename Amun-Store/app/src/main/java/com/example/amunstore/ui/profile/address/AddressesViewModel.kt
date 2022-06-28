package com.example.amunstore.ui.profile.address

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.amunstore.data.model.address.Address
import com.example.amunstore.data.repositories.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddressesViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    var defaultAddress = MutableLiveData<Address>()
    var addressList = MutableLiveData<List<Address>>()

    suspend fun getUserAddresses() {
        val response = userRepository.getUserAddresses(userRepository.getCustomerId())

        val (match, rest) = response.addresses.partition { it.default == true }

        if (match.isNotEmpty())
            defaultAddress.postValue(match[0])
        addressList.postValue(rest)

        Log.d("addresses", response.toString())
    }

    suspend fun deleteUserAddress(address: Address) {
        userRepository.deleteUserAddress(
            userRepository.getCustomerId().toString(),
            address.id.toString()
        )
    }

    suspend fun setAddressAsDefault(address: Address) {
        userRepository.setAddressAsDefault(
            userRepository.getCustomerId().toString(),
            address.id.toString()
        )
    }

}