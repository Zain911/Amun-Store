package com.example.example

import com.google.gson.annotations.SerializedName


data class PriceRules(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("value_type") var valueType: String? = null,
    @SerializedName("value") var value: String? = null,
    @SerializedName("once_per_customer") var oncePerCustomer: Boolean? = null,
    @SerializedName("usage_limit") var usageLimit: String? = null,
    @SerializedName("starts_at") var startsAt: String? = null,
    @SerializedName("ends_at") var endsAt: String? = null,
    @SerializedName("created_at") var createdAt: String? = null,
    @SerializedName("title") var title: String? = null
)