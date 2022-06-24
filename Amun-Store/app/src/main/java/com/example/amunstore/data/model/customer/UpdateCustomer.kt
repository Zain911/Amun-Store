package com.example.amunstore.data.model.customer

import com.google.gson.annotations.SerializedName

data class UpdateCustomer(

    @SerializedName("multipass_identifier") var idDraftCart: String? = null,
    @SerializedName("tags") var idDraftFavourite: String? = null,
)
