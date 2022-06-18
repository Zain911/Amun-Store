package com.example.amunstore.ui.cart

import androidx.lifecycle.*
import com.example.amunstore.data.model.cart.ItemCart
import com.example.amunstore.data.model.order.AddOrderRequestModel
import com.example.amunstore.data.model.order.Customer
import com.example.amunstore.data.model.order.Order
import com.example.amunstore.data.repositories.cart.CartRepository
import com.example.amunstore.data.repositories.orders.OrdersRepository
import com.example.amunstore.data.repositories.user.UserRepository
import com.example.example.LineItems
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val cartRepository: CartRepository,
    private val ordersRepository: OrdersRepository,
) : ViewModel() {

    var cartItems = MutableLiveData<List<ItemCart>>()

    var data: LiveData<List<ItemCart>> = cartItems

    var userName = MutableLiveData<String>()
    var userAddress = MutableLiveData<String>()

    fun loadUserName() {
        userName.postValue(userRepository.getUserName())
    }

    private val x = Observer<List<ItemCart>> {
        cartItems.postValue(it)
    }

    suspend fun getCartItems() {
        cartRepository.getAllItemCart().observeForever(x)
    }

    override fun onCleared() {
        super.onCleared()
        cartItems.removeObserver(x)
    }

    fun removeItem(itemCart: ItemCart) {
        cartRepository.deleteItem(itemCart)
    }

    fun increaseItemQuantity(itemCart: ItemCart) {
        if ((itemCart.maxItem ?: 0) >= (itemCart.item_number ?: 0))
            cartRepository.updateItem(itemCart.apply { item_number = item_number?.plus(1) })
    }

    fun decreaseItemQuantity(itemCart: ItemCart) {
        if (itemCart.item_number !in 0..1)
            cartRepository.updateItem(itemCart.apply {
                item_number = item_number?.minus(1)
            })
    }

    suspend fun getUserAddresses() {
        val addresses = userRepository.getUserAddresses(userRepository.getCustomerId())
        val addressesFiltered = addresses.addresses.filter {
            it.default == true
        }
        if (addressesFiltered.isNotEmpty())
            userAddress.postValue(addressesFiltered[0].address1)
    }

    suspend fun addUserOrder(discount: Float) {
        var order: AddOrderRequestModel = AddOrderRequestModel()
        val lineItems = mutableListOf<LineItems>()
        var totalPrice = 0.0f
        for (item in cartItems.value!!) {
            lineItems.add(LineItems(id = item.id, quantity = item.item_number))
            totalPrice = totalPrice.plus(item.price?.toFloat()!!)
        }
        order.order =
            Order(lineItems = lineItems as ArrayList<LineItems>,
                customer = Customer(id = userRepository.getCustomerId()),
                email = userRepository.getUserEmail(),
                totalPrice = totalPrice.toString(),
                totalDiscounts = discount.toString())

        order.financial_status = "paid"

        ordersRepository.addUserOrder(order)
    }


}