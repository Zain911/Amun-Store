package com.example.amunstore.ui.cart

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.amunstore.data.model.cart.ItemCart
import com.example.amunstore.data.model.draftorder.*
import com.example.amunstore.data.model.order.*
import com.example.amunstore.data.presistentstorage.sharedprefs.UserSharedPreferences
import com.example.amunstore.data.repositories.cart.CartRepository
import com.example.amunstore.data.repositories.draftorder.DraftOrderRepository
import com.example.amunstore.data.repositories.orders.OrdersRepository
import com.example.amunstore.data.repositories.user.UserRepository
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val cartRepository: CartRepository,
    private val ordersRepository: OrdersRepository,
    private val draftOrderRepository: DraftOrderRepository,
    private val sharedPreferences: UserSharedPreferences,
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

    fun addUserOrder(discount: Float): AddOrderRequestModel {

        val order = AddOrderRequestModel()

        val lineItems = ArrayList<LineItems>()

        var totalPrice = 0.0f

        for (item in cartItems.value!!) {
            lineItems.add(LineItems(variantId = item.variant_id, quantity = item.item_number))
            totalPrice = totalPrice.plus(item.price?.toFloat()!!)
        }

        order.order = OrderRequest(
            customer = OrderCustomer(userRepository.getUserEmail()),
            lineItems = lineItems,
            shippingAddress = OrderShippingAddress(userAddress.value),
            totalPrice = totalPrice.toString(),
            totalDiscounts = discount.toString()
        )
        return order
    }

    suspend fun updateUserDraftOrder() {


        val lineItems = ArrayList<DraftOrderLineItemModel>()
        for (item in cartItems.value!!) {
            lineItems.add(DraftOrderLineItemModel(variantId = item.variant_id,
                quantity = item.item_number))
        }

        val draftOrder = DraftOrderRequest(
            draftOrder = DraftOrderRequestModel(
                lineItems = lineItems,
                customer = DraftOrderRequestCustomer(
                    id = "6268209725698",
                    email = userRepository.getUserEmail()
                )
            )
        )
        val gson = Gson()
        Log.d("DraftOrder", gson.toJson(draftOrder).toString())

        //TODO change the default draftOrder and user From Shared Preferences
        //draftOrderRepository.updateDraftOrder(userRepository.getCartDraftOrderIdFromSharedPrefs() , draftOrder)
        if (sharedPreferences.getCartDraftOrderId() != "0") {
            val response = draftOrderRepository.updateDraftOrder(sharedPreferences.getCartDraftOrderId(), draftOrder)
            Log.d("DraftOrder", response.toString())
            Log.d( "DraftOrder Shard pref: ", sharedPreferences.getCartDraftOrderId())
            Log.d( "respons body: ",response.body().toString())
        } else {
            val response = draftOrderRepository.createDraftOrder(draftOrder)
            if (response.isSuccessful)
                setDraftCartId(response.body()?.draftOrder?.id.toString())
            Log.d("DraftOrder create", response.toString())
            Log.d( "respons body: ",response.body().toString())


        }

    }

    private fun setDraftCartId(id: String?) {
        Log.d("DraftOrder id", id.toString())

        sharedPreferences.setCartDraftOrderId(id ?: "0")

    }

}