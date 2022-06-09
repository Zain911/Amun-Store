package com.example.amunstore.ui.cart.coupon

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.amunstore.data.repositories.user.UserRepository
import com.example.amunstore.data.model.address.Address
import com.example.amunstore.data.repositories.coupon.DiscountRepository
import com.example.example.PriceRules
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CouponViewModel @Inject constructor(private val discountRepository: DiscountRepository) : ViewModel() {

    val couponList = MutableLiveData<List<PriceRules>?>()
    suspend fun getAllCoupons() {

        val list = discountRepository.getDiscountCodes()
        couponList.postValue(list.body()?.priceRules)
    }
}