package com.example.amunstore.data.model.order

import com.google.gson.annotations.SerializedName

data class AddOrderRequestModel(
    @SerializedName("order") var order: OrderRequest? = null,
)

data class OrderRequest(
    @SerializedName("customer") var customer: OrderCustomer?,
    @SerializedName("line_items") var lineItems: ArrayList<LineItems> = arrayListOf(),
    @SerializedName("shipping_address") var shippingAddress: OrderShippingAddress? = null,
    @SerializedName("financial_status") var financialStatus: String? = null,
    @SerializedName("total_discounts") var totalDiscounts: String? = null,
    @SerializedName("total_price") var totalPrice: String? = null
    )

data class OrderCustomer(
    @SerializedName("email") var email: String? = null
)

data class OrderShippingAddress(
    @SerializedName("address1") var address1: String? = null
)
