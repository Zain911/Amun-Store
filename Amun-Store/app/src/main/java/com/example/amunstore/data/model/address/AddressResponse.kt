package com.example.amunstore.data.model.address

import com.google.gson.annotations.SerializedName


data class AddressResponse (

    @SerializedName("addresses" ) var addresses : ArrayList<Address> = arrayListOf()

)
