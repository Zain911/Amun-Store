package com.example.amunstore.ui.main

import android.util.Log
import androidx.lifecycle.*
import com.example.amunstore.data.model.cart.ItemCart
import com.example.amunstore.data.model.customer.RequestCartDraftOrder
import com.example.amunstore.data.model.customer.RequestFavouriteDraftOrder
import com.example.amunstore.data.model.customer.SetDraftOrderId
import com.example.amunstore.data.model.customer.SetFavouriteOrderId
import com.example.amunstore.data.model.draftorder.*
import com.example.amunstore.data.model.order.LineItems
import com.example.amunstore.data.model.product.Product
import com.example.amunstore.data.presistentstorage.sharedprefs.UserSharedPreferences
import com.example.amunstore.data.repositories.cart.CartRepository
import com.example.amunstore.data.repositories.draftorder.DraftOrderRepository
import com.example.amunstore.data.repositories.products.ProductsRepository
import com.example.amunstore.data.repositories.user.UserRepository
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val favouriteRepository: ProductsRepository,
    private val cartRepository: CartRepository,
    private val userRepository: UserRepository,
    private val draftOrderRepository: DraftOrderRepository,
    private val sharedPreferences: UserSharedPreferences,
) : ViewModel() {

    var cartItemsCount = MutableLiveData<Int>()
    var favouriteItemsCount = MutableLiveData<Int>()

    private val cartItemsCountObserver = Observer<Int> {
        cartItemsCount.postValue(it)
    }

    private val favouriteItemsCountObserver = Observer<Int> {
        favouriteItemsCount.postValue(it)
    }

    private val cartItemsObserver = Observer<List<ItemCart>> {

        if (it.isEmpty()) {
            GlobalScope.launch {
                setEmptyOrder()
            }
        } else {
            // send data to api
            Log.d("DebugCart dataObserver: ", it.toString())
            val lineItems = ArrayList<DraftOrderLineItemModel>()

            for (item in it) {
                lineItems.add(
                    DraftOrderLineItemModel(
                        variantId = item.variant_id,
                        quantity = item.item_number,
                        properties = arrayListOf(
                            Property("src", item.src),
                            Property("max_item", item.maxItem.toString())
                        )
                    )
                )
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

            Log.d("DebugCart userId", sharedPreferences.getCustomerId().toString())
            Log.d("DebugCart cartDraftOrderId", sharedPreferences.getCartDraftOrderId())

            GlobalScope.launch {
                if (sharedPreferences.getCartDraftOrderId().isNotEmpty()) {
                    val response =
                        draftOrderRepository.updateDraftOrder(
                            sharedPreferences.getCartDraftOrderId(),
                            draftOrder
                        )
                    Log.d("DebugCart responseUpdateDraftOrder", response.body().toString())

                } else {
                    val response = draftOrderRepository.createDraftOrder(draftOrder)

                    Log.d("DebugCart createDraftOrder", response.body().toString())
                    if (response.isSuccessful) {
                        sharedPreferences.setCartDraftOrderId(response.body()?.draftOrder?.id.toString())
                        val response2 = userRepository.setUserCartDraftOrderId(
                            userRepository.getCustomerId().toString(),
                            RequestCartDraftOrder(draftOrderId = SetDraftOrderId(response.body()?.draftOrder?.id.toString()))
                        )
                        Log.d("DebugCart setUserCartDraftOrderId", response2.body().toString())
                    }
                }
            }
        }

    }

    private val favoriteItemsObserver = Observer<List<Product>> {
        if (it.isEmpty()) {
            GlobalScope.launch {
                setEmptyFavourite()
            }
        } else {
            Log.d("DebugFavourite dataObserver: ", it.toString())
            var json= Gson()
            json.toJson(it.toString())
            Log.d("JSONFavourite", json.toString())
            val lineItems = ArrayList<DraftOrderLineItemModel>()

            for (item in it) {
                lineItems.add(
                    DraftOrderLineItemModel(
                        variantId = item.mainVariant,
                        quantity = 1,
                        properties = arrayListOf(
                            Property("src", item.imageSrc),
                            Property("vendor", item.vendor),
                            Property("product_type", item.productType)
                        )
                    )
                )
            }

            val draftOrder = DraftOrderRequest(
                draftOrder = DraftOrderRequestModel(
                    lineItems = lineItems,
                    customer = DraftOrderRequestCustomer(
                        id = userRepository.getCustomerId().toString(),
                        email = userRepository.getUserEmail()
                    )
                )
            )

            Log.d("DebugFavourite userId", sharedPreferences.getCustomerId().toString())
            Log.d("DebugFavourite cartDraftFavouriteId", sharedPreferences.getFavouriteOrderId())

            GlobalScope.launch {
                if (sharedPreferences.getFavouriteOrderId().isNotEmpty()) {
                    val response =
                        draftOrderRepository.updateDraftOrder(
                            sharedPreferences.getFavouriteOrderId(),
                            draftOrder
                        )
                    Log.d("DebugFavourite responseUpdateDraftOrder", response.body().toString())

                } else {
                    val response = draftOrderRepository.createDraftOrder(draftOrder)

                    Log.d("DebugFavourite createDraftOrder", response.body().toString())
                    if (response.isSuccessful) {
                        sharedPreferences.setFavouriteOrderId(response.body()?.draftOrder?.id.toString())
                        val response2 = userRepository.setUserFavouriteDraftOrderId(
                            userRepository.getCustomerId().toString(),
                            RequestFavouriteDraftOrder(draftOrderId = SetFavouriteOrderId(response.body()?.draftOrder?.id.toString()))
                        )
                        Log.d("DebugCart setUserCartDraftOrderId", response2.body().toString())
                    }
                }
            }
        }
    }

    fun getCartItemsCount() {
        cartRepository.getCartItemsCount().observeForever(cartItemsCountObserver)
    }

    fun getFavouriteItemsCount() {
        favouriteRepository.getFavouritesItemCount().observeForever(favouriteItemsCountObserver)
    }

    suspend fun getFavoriteItems() {
        favouriteRepository.getAllFavouriteProducts().observeForever(favoriteItemsObserver)
    }

    suspend fun getCartItems() {
        cartRepository.getAllItemCart().observeForever(cartItemsObserver)
    }

    private suspend fun setEmptyOrder() {
        sharedPreferences.setCartDraftOrderId("")
        userRepository.setUserCartDraftOrderId(
            userRepository.getCustomerId().toString(), RequestCartDraftOrder(
                SetDraftOrderId(idDraftCart = "")
            )
        )
    }

    private suspend fun setEmptyFavourite() {
        sharedPreferences.setFavouriteOrderId("")
        userRepository.setUserFavouriteDraftOrderId(
            userRepository.getCustomerId().toString(), RequestFavouriteDraftOrder(
                SetFavouriteOrderId(idDraftFavourite = "")
            )
        )
    }


    override fun onCleared() {
        super.onCleared()
        cartRepository.getCartItemsCount().removeObserver(cartItemsCountObserver)
        favouriteRepository.getFavouritesItemCount().removeObserver(favouriteItemsCountObserver)
    }

    suspend fun getCartDraftOrder() {
        if (userRepository.getCartDraftOrderIdFromSharedPrefs().isNotEmpty()) {
            val response =
                draftOrderRepository.getDraftOrderById(userRepository.getCartDraftOrderIdFromSharedPrefs())

            if (response.isSuccessful) {
                for (item in response.body()?.draftOrder?.lineItems!!)
                    cartRepository.addItem(
                        ItemCart(
                            id = item.productId,
                            title = item.title,
                            price = item.price,
                            size = item.variantTitle?.substringBefore("/"),
                            src = item.properties[0].value,
                            maxItem = item.properties[1].value?.toInt(),
                            item_number = item.quantity,
                            variant_id = item.variantId
                        )
                    )
            }
        }

        getCartItems()
    }

    suspend fun getFavouriteDraftOrder() {
        if (userRepository.getFavouriteDraftOrderIdFromSharedPrefs().isNotEmpty()) {
            val response =
                draftOrderRepository.getDraftOrderById(userRepository.getFavouriteDraftOrderIdFromSharedPrefs())

            if (response.isSuccessful) {
                for (item in response.body()?.draftOrder?.lineItems!!)
                    favouriteRepository.addProductToFavourite(
                        Product(
                            id = item.productId,
                            title = item.title,
                            imageSrc = item.properties[0].value,
                            vendor = item.properties[1].value,
                            productType = item.properties[2].value,
                            mainVariant = item.variantId
                        )
                    )
            }
        }

        getFavoriteItems()
    }

}