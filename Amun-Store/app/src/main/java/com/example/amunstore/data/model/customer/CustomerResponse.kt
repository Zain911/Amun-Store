package com.example.amunstore.data.model.customer

import com.example.amunstore.data.model.order.Customer
import com.google.gson.annotations.SerializedName

data class CustomerResponse(
    val customers: List<Customer>,
)

data class CustomerSingleResponse(
    @SerializedName("customer") val customer: Customer,
)