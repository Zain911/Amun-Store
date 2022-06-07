package com.example.amunstore.ui.cart.addresses

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.amunstore.data.repositories.user.UserRepository
import com.example.amunstore.data.model.address.Address
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddressesViewModel @Inject constructor(private val userRepo: UserRepository) : ViewModel() {

    val addressesList = MutableLiveData<List<Address>?>()
    suspend fun getUserAddresses() {

        //TODO remove static customer id and get it from sharedPref
        //val list = userRepo.getUserAddresses(userRepo.getCustomerId())
        val list = userRepo.getUserAddresses(6465366753509)
        addressesList.postValue(list.addresses)
    }
}