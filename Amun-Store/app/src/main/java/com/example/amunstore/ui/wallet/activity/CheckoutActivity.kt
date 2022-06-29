/*
 * Copyright 2021 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.amunstore.ui.wallet.activity

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts.StartIntentSenderForResult
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.amunstore.R
import com.example.amunstore.data.model.order.AddOrderRequestModel
import com.example.amunstore.databinding.ActivityCheckoutBinding
import com.example.amunstore.domain.util.InternetConnectivity
import com.example.amunstore.ui.auth.login.OrderCompletedFragment
import com.example.amunstore.ui.wallet.viewmodel.CheckoutViewModel
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.wallet.PaymentData
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONException
import org.json.JSONObject

/**
 * Checkout implementation for the app
 */
@AndroidEntryPoint
class CheckoutActivity : AppCompatActivity() {

    private val model: CheckoutViewModel by viewModels()

    private lateinit var layout: ActivityCheckoutBinding
    private lateinit var googlePayButton: View

    private var price: Double = 0.0
    private var disscount: Double = 0.0
    private lateinit var address: String
    private lateinit var myOrder: AddOrderRequestModel

    lateinit var bottomFragment: OrderCompletedFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myOrder = (intent.extras?.getSerializable("order") as AddOrderRequestModel?)!!
        // Use view binding to access the UI elements
        layout = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(layout.root)

        // Setup buttons
        googlePayButton = layout.googlePayButton.root
        googlePayButton.setOnClickListener {
            val builder =
                AlertDialog.Builder(this, R.style.AlertDialogTheme)
                    .setCancelable(false)
                    .setMessage(getString(R.string.do_you_want_to_complete_payment_with_google_pay))
                    .setPositiveButton(getString(R.string.yes)) { dialog, _ ->

                        if (myOrder.order?.shippingAddress?.address1.isNullOrEmpty())
                            Toast.makeText(
                                this,
                                getString(R.string.no_address_available),
                                Toast.LENGTH_LONG
                            )
                                .show()
                        else
                            requestPayment(myOrder)


                        dialog.dismiss()
                    }.setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                        dialog.dismiss()
                    }

            val alert = builder.create()
            alert.show()
        }

        // Check Google Pay availability
        model.canUseGooglePay.observe(this, Observer(::setGooglePayAvailable))


        price = myOrder.order?.totalPrice!!.toDouble()
        disscount = myOrder.order?.totalDiscounts!!.toDouble()
        address = myOrder.order?.shippingAddress?.address1.toString()

        if (price - disscount > 0) {
            layout.detailPrice.text = " ${price + disscount} L.E"
            layout.totalPriceTextView.text = "$price L.E"
            layout.discountTextView.text = "$disscount L.E"

        } else
            layout.detailPrice.text = "price: ${0} L.E"
        if (myOrder.order?.shippingAddress?.address1.isNullOrEmpty())
            layout.detailAddress.text = getString(R.string.no_address_available)
        layout.detailAddress.text = address

        val cashOnDeliveryButton = layout.cashOnDeliveryButton
        cashOnDeliveryButton.setOnClickListener {
            val builder =
                AlertDialog.Builder(this, R.style.AlertDialogTheme)
                    .setCancelable(false)
                    .setMessage(getString(R.string.do_you_want_to_complete_order_with_cash_on_delivery))
                    .setPositiveButton(getString(R.string.yes)) { dialog, _ ->
                        if (myOrder.order?.shippingAddress?.address1.isNullOrEmpty())
                            Toast.makeText(
                                this,
                                getString(R.string.no_address_available),
                                Toast.LENGTH_LONG
                            )
                                .show()
                        else
                            completePayment("pending")


                        dialog.dismiss()
                    }.setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                        dialog.dismiss()
                    }

            val alert = builder.create()
            alert.show()

        }
    }

    /**
     * If isReadyToPay returned `true`, show the button and hide the "checking" text. Otherwise,
     * notify the user that Google Pay is not available. Please adjust to fit in with your current
     * user flow. You are not required to explicitly let the user know if isReadyToPay returns `false`.
     *
     * @param available isReadyToPay API response.
     */
    private fun setGooglePayAvailable(available: Boolean) {
        if (available) {
            googlePayButton.visibility = View.VISIBLE
        } else {
            Toast.makeText(
                this,
                R.string.google_pay_status_unavailable,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun requestPayment(orderRequestModel: AddOrderRequestModel) {

        // Disables the button to prevent multiple clicks.
        googlePayButton.isClickable = false

        // The price provided to the API should include taxes and shipping.
        // This price is not displayed to the user.
        val dummyPriceCents = orderRequestModel.order?.totalPrice?.toDouble()
        val shippingCostCents = 0L
        val total = dummyPriceCents?.plus(shippingCostCents)
        val task = model.getLoadPaymentDataTask(total?.toLong() ?: 0)

        task.addOnCompleteListener { completedTask ->
            if (completedTask.isSuccessful) {
                completedTask.result.let(::handlePaymentSuccess)
            } else {
                when (val exception = completedTask.exception) {
                    is ResolvableApiException -> {
                        resolvePaymentForResult.launch(
                            IntentSenderRequest.Builder(exception.resolution).build()
                        )
                    }
                    is ApiException -> {
                        handleError(exception.statusCode, exception.message)
                    }
                    else -> {
                        handleError(
                            CommonStatusCodes.INTERNAL_ERROR, "Unexpected non API" +
                                    " exception when trying to deliver the task result to an activity!"
                        )
                    }
                }
            }

            // Re-enables the Google Pay payment button.
            googlePayButton.isClickable = true
        }
    }

    // Handle potential conflict from calling loadPaymentData
    private val resolvePaymentForResult =
        registerForActivityResult(StartIntentSenderForResult()) { result: ActivityResult ->
            when (result.resultCode) {
                RESULT_OK ->
                    result.data?.let { intent ->
                        PaymentData.getFromIntent(intent)?.let(::handlePaymentSuccess)
                        completePayment("paid")
                    }
                RESULT_CANCELED -> {
                    Toast
                        .makeText(this, getString(R.string.canceled), Toast.LENGTH_LONG)
                        .show()
                    // The user cancelled the payment attempt
                }
            }
        }

    private fun completePayment(financial: String) {
        var connectionLiveData = InternetConnectivity(context = baseContext!!)
        connectionLiveData.observe(this) {
            if (it) {
                model.createOrder(myOrder, financial_status = financial)
                showBottomSheetDialogFragment()
            } else
                Toast.makeText(this, "check Internet Connection", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * PaymentData response object contains the payment information, as well as any additional
     * requested information, such as billing and shipping address.
     *
     * @param paymentData A response object returned by Google after a payer approves payment.
     * @see [Payment
     * Data](https://developers.google.com/pay/api/android/reference/object.PaymentData)
     */
    private fun handlePaymentSuccess(paymentData: PaymentData) {
        val paymentInformation = paymentData.toJson()

        try {
            // Token will be null if PaymentDataRequest was not constructed using fromJson(String).
            val paymentMethodData =
                JSONObject(paymentInformation).getJSONObject("paymentMethodData")
            val billingName = paymentMethodData.getJSONObject("info")
                .getJSONObject("billingAddress").getString("name")
            Log.d("BillingName", billingName)

            Toast.makeText(
                this,
                getString(R.string.payments_show_name, billingName),
                Toast.LENGTH_LONG
            ).show()

            // Logging token string.
            Log.d(
                "Google Pay token", paymentMethodData
                    .getJSONObject("tokenizationData")
                    .getString("token")
            )

        } catch (error: JSONException) {
            Log.e("handlePaymentSuccess", "Error: $error")
        }
    }

    /**
     * At this stage, the user has already seen a popup informing them an error occurred. Normally,
     * only logging is required.
     *
     * @param statusCode will hold the value of any constant from CommonStatusCode or one of the
     * WalletConstants.ERROR_CODE_* constants.
     * @see [
     * Wallet Constants Library](https://developers.google.com/android/reference/com/google/android/gms/wallet/WalletConstants.constant-summary)
     */
    private fun handleError(statusCode: Int, message: String?) {
        Log.e("Google Pay API error", "Error code: $statusCode, Message: $message")
    }

    private fun showBottomSheetDialogFragment() {
        bottomFragment = OrderCompletedFragment()
        if (!bottomFragment.isVisible)
            bottomFragment.show(supportFragmentManager, bottomFragment.tag)
    }
}
