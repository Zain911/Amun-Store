package com.example.amunstore.data.model.address

import com.google.gson.annotations.SerializedName

data class AddingAddressResponseModel (

    @SerializedName("customer_address" ) var customerAddress : Address? = null

)