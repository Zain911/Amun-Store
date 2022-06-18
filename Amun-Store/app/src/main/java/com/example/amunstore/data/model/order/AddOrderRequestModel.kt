package com.example.amunstore.data.model.order

import com.example.amunstore.data.model.address.Address
import com.google.gson.annotations.SerializedName

data class AddOrderRequestModel(
    @SerializedName("order") var order: Order? = null,
    @SerializedName("customer") var customer: Customer? = null,
    @SerializedName("shipping_address") var address: Address? = null,
    @SerializedName("financial_status")var financial_status:String?=null
)
