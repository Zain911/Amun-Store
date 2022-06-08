package com.example.amunstore.data.network

import com.example.amunstore.data.model.discount.PriceRuleResponse
import retrofit2.Response
import retrofit2.http.GET

interface DiscountService {
    @GET("price_rules.json")
    suspend fun getDiscountCodes(
    ): Response<PriceRuleResponse>

}