package com.example.amunstore.ui.wallet.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.amunstore.R
import com.example.amunstore.data.model.order.AddOrderRequestModel
import com.example.amunstore.data.repositories.cart.CartRepository
import com.example.amunstore.data.repositories.orders.OrdersRepository
import com.example.amunstore.data.repositories.user.UserRepository
import com.example.amunstore.ui.wallet.util.PaymentsUtil
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.pay.Pay
import com.google.android.gms.pay.PayApiAvailabilityStatus
import com.google.android.gms.pay.PayClient
import com.google.android.gms.tasks.Task
import com.google.android.gms.wallet.IsReadyToPayRequest
import com.google.android.gms.wallet.PaymentData
import com.google.android.gms.wallet.PaymentDataRequest
import com.google.android.gms.wallet.PaymentsClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class CheckoutViewModel @Inject constructor(
    application: Application,
    private val ordersRepo: OrdersRepository, private val userRepo: UserRepository ,private val cartRepo : CartRepository
) : AndroidViewModel(application) {

    // A client for interacting with the Google Pay API.
    private val paymentsClient: PaymentsClient = PaymentsUtil.createPaymentsClient(application)

    // A client to interact with the Google Wallet API
    private val walletClient: PayClient = Pay.getClient(application)

    // LiveData with the result of whether the user can pay using Google Pay
    private val _canUseGooglePay: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>().also {
            fetchCanUseGooglePay()
        }
    }

    // LiveData with the result of whether the user can save passes to Google Wallet
    private val _canSavePasses: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>().also {
            fetchCanAddPassesToGoogleWallet()
        }
    }

    val canUseGooglePay: LiveData<Boolean> = _canUseGooglePay

    /**
     * Determine the user's ability to pay with a payment method supported by your app and display
     * a Google Pay payment button.
     *
     * @return a [LiveData] object that holds the future result of the call.
     * @see [](https://developers.google.com/android/reference/com/google/android/gms/wallet/PaymentsClient.html.isReadyToPay)
    ) */
    private fun fetchCanUseGooglePay() {
        val isReadyToPayJson = PaymentsUtil.isReadyToPayRequest()
        if (isReadyToPayJson == null) _canUseGooglePay.value = false

        val request = IsReadyToPayRequest.fromJson(isReadyToPayJson.toString())
        val task = paymentsClient.isReadyToPay(request)
        task.addOnCompleteListener { completedTask ->
            try {
                _canUseGooglePay.value = completedTask.getResult(ApiException::class.java)
            } catch (exception: ApiException) {
                Log.w("isReadyToPay failed", exception)
                _canUseGooglePay.value = false
            }
        }
    }

    /**
     * Creates a [Task] that starts the payment process with the transaction details included.
     *
     * @param priceCents the price to show on the payment sheet.
     * @return a [Task] with the payment information.
     * @see [](https://developers.google.com/android/reference/com/google/android/gms/wallet/PaymentsClient#loadPaymentData(com.google.android.gms.wallet.PaymentDataRequest)
    ) */
    fun getLoadPaymentDataTask(priceCents: Long): Task<PaymentData> {
        val paymentDataRequestJson = PaymentsUtil.getPaymentDataRequest(priceCents)
        val request = PaymentDataRequest.fromJson(paymentDataRequestJson.toString())
        return paymentsClient.loadPaymentData(request)
    }

    /**
     * Determine whether the API to save passes to Google Pay is available on the device.
     */
    private fun fetchCanAddPassesToGoogleWallet() {
        walletClient
            .getPayApiAvailabilityStatus(PayClient.RequestType.SAVE_PASSES)
            .addOnSuccessListener { status ->
                _canSavePasses.value = status == PayApiAvailabilityStatus.AVAILABLE
                // } else {
                // We recommend to either:
                // 1) Hide the save button
                // 2) Fall back to a different Save Passes integration (e.g. JWT link)
                // Note that a user might become eligible in the future.
            }
            .addOnFailureListener {
                // Google Play Services is too old. API availability can't be verified.
                _canUseGooglePay.value = false
            }
    }

    /*
    {"order":{"email":"foo@example.com","fulfillment_status":"fulfilled","line_items":[{"variant_id":447654529,"quantity":1}]}}
    {"order":{"line_items":[{"variant_id":447654529,"quantity":1}],"customer":{"id":207119551},"financial_status":"pending"}}'
    */
    fun createOrder(orderRequestModel: AddOrderRequestModel ,financial_status:String) {
        CoroutineScope(Dispatchers.IO).launch {
            // Create JSON using JSONObject
            val orderBody = JSONObject()
            val jsonObject = JSONObject()
            val customer = JSONObject().put("id", userRepo.getCustomerId())
            jsonObject.put("customer", customer)
            jsonObject.put("email", orderRequestModel.order?.customer?.email)
//            jsonObject.put("fulfillment_status", "fulfilled")
            jsonObject.put("currency", "EGP")
            jsonObject.put("financial_status", financial_status)

            val lineItems = JSONArray()
            for (item in orderRequestModel.order?.lineItems!!) {
                val lineItem = JSONObject()
                lineItem.put("variant_id", item.variantId)
                lineItem.put("quantity", item.quantity)
                lineItems.put(lineItem)
            }
            jsonObject.put("line_items", lineItems)

            // Convert JSONObject to String
            val jsonObjectString = orderBody.put("order", jsonObject).toString()

            val body: RequestBody =
                RequestBody.create(MediaType.parse("application/json"), jsonObjectString)

            val response = ordersRepo.addUserOrder(body)

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    clearAllCart()
                    Toast.makeText(getApplication(), getApplication<Application>().getString(R.string.succuss), Toast.LENGTH_SHORT).show()
                    Toast.makeText(getApplication(),getApplication<Application>().getString(R.string.thank_you_your_order_will_be_shipped_soon),Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(
                        getApplication(),
                        response.code().toString() + getApplication<Application?>().getString(
                            R.string.please_try_again_later_or_contact_us
                        ),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    fun clearAllCart(){
        cartRepo.clearAllCart()
    }
    // Test generic object used to be created against the API
    val genericObjectJwt =
        "eyJ0eXAiOiAiSldUIiwgImFsZyI6ICJSUzI1NiIsICJraWQiOiAiMTY4M2VjZDA1MmU5NTgyZWZhNGU5YTQxNjVmYzE5N2JjNmJlYTJhMCJ9.eyJpc3MiOiAid2FsbGV0LWxhYi10b29sc0BhcHBzcG90LmdzZXJ2aWNlYWNjb3VudC5jb20iLCAiYXVkIjogImdvb2dsZSIsICJ0eXAiOiAic2F2ZXRvd2FsbGV0IiwgImlhdCI6IDE2NTA1MzI2MjMsICJwYXlsb2FkIjogeyJnZW5lcmljT2JqZWN0cyI6IFt7ImlkIjogIjMzODgwMDAwMDAwMjIwOTUxNzcuZjUyZDRhZjYtMjQxMS00ZDU5LWFlNDktNzg2ZDY3N2FkOTJiIn1dfX0.fYKw6fpLfwwNMi5OGr4ybO3ybuCU7RYjQhw-QM_Z71mfOyv2wFUzf6dKgpspJKQmkiaBWBr1L9n8jq8ZMfj6iOA_9_njfUe9GepCwVLC0nZBDd2EqS3UrBYT7tEmk7W2-Cpy5FJFTt_eiqXBZgwa6vMw6e6mMp-GzSD5_ls39fjOPziboLyG-GDmph3f6UhBkjnUjYyY_FoYdlqkTkCWM7AFPcy-FbRyVDpIaHfVk4eYQi4Vzk0fwxaWWTfP3gSXXT6UJ9aFvaPYs0gnlV2WPVgGGKCMtYHFRGYX1t0WRpN2kbxfO5VuMKWJlz3TCnxp-9Axz-enuCgnq2cLvCk6Tw"
}