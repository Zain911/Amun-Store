package com.example.amunstore.data.model.customer

import com.google.gson.annotations.SerializedName

data class SetDraftOrderId(

    @SerializedName("multipass_identifier") var idDraftCart: String? = null,
)
data class SetFavouriteOrderId(

    @SerializedName("tags") var idDraftFavourite: String? = null,
)
data class RequestCartDraftOrder(
    @SerializedName("customer") var draftOrderId: SetDraftOrderId?=null
)
data class RequestFavouriteDraftOrder(
    @SerializedName("customer") var draftOrderId: SetFavouriteOrderId?=null
)
