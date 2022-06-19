package com.example.amunstore.ui.orders

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.amunstore.data.model.order.Order
import com.example.amunstore.data.repositories.orders.OrdersRepository
import com.example.amunstore.data.repositories.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val ordersRepository: OrdersRepository
) : ViewModel() {

    val ordersList = MutableLiveData<List<Order>>()

    suspend fun getUserOrders() {
        //TODO change static customer id to real customer id from shared prefs
        ordersList.postValue(ordersRepository.getUserOrders(userRepository.getCustomerId()).orders)
        //ordersList.postValue(ordersRepository.getUserOrders(6252021154050).orders)
    }

    suspend fun removeOrder(order: Order) {
        ordersRepository.deleteOrder(order.id!!)

    }

}