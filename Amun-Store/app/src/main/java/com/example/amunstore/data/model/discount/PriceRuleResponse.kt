package com.example.amunstore.data.model.discount


import com.example.example.PriceRules
import com.google.gson.annotations.SerializedName

class PriceRuleResponse {
    @SerializedName("price_rules" ) var products : ArrayList<PriceRules> = arrayListOf()

}