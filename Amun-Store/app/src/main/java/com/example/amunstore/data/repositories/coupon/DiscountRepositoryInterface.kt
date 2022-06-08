package com.example.amunstore.data.repositories.coupon

import com.example.amunstore.data.model.discount.PriceRuleResponse
import retrofit2.Response

interface DiscountRepositoryInterface {

    suspend fun getDiscountCodes() :Response<PriceRuleResponse>
}