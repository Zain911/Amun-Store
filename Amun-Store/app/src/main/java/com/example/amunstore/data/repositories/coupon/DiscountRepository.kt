package com.example.amunstore.data.repositories.coupon

import com.example.amunstore.data.model.discount.PriceRuleResponse
import com.example.amunstore.data.network.DiscountService
import retrofit2.Response

class DiscountRepository(private val discountService: DiscountService) :
    DiscountRepositoryInterface {

    override suspend fun getDiscountCodes(): Response<PriceRuleResponse> =
        discountService.getDiscountCodes()


}