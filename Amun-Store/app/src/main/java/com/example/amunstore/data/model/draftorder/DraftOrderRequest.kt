package com.example.amunstore.data.model.draftorder

import com.example.amunstore.data.model.order.LineItems
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class DraftOrderRequest(
    @SerializedName("draft_order") var draftOrder: DraftOrderRequestModel? = null,
)

data class DraftOrderRequestModel(
    @SerializedName("line_items") var lineItems: ArrayList<DraftOrderLineItemModel>? = arrayListOf(),
    @SerializedName("customer") var customer: DraftOrderRequestCustomer
) : Serializable

data class DraftOrderRequestCustomer(
    @SerializedName("email") var email: String? = null,
    @SerializedName("id") var id: String? = null
) : Serializable

data class DraftOrderLineItemModel(
    @SerializedName("variant_id") var variantId: Long? = null,
    @SerializedName("quantity") var quantity: Int? = null,
    @SerializedName("properties") var properties: ArrayList<Property>? = null
)

data class Property(
    @SerializedName("name") var name: String? = null,
    @SerializedName("value") var value: String? = null
)


