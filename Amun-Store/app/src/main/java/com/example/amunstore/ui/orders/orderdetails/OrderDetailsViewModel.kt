package com.example.amunstore.ui.orders.orderdetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.amunstore.data.model.order.Order
import com.example.amunstore.data.repositories.orders.OrdersRepository
import com.example.amunstore.data.repositories.products.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OrderDetailsViewModel @Inject constructor(
    private val ordersRepo: OrdersRepository,
    val productsRepository: ProductsRepository
) :
    ViewModel() {

    val order = MutableLiveData<Order>()

    suspend fun getOrderById(orderId: Long) {
        val response = ordersRepo.getOrderById(orderId)
        if (response.isSuccessful)
            order.postValue(response.body()?.order!!)
    }
}