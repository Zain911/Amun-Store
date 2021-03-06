package com.example.amunstore.ui.cart

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.amunstore.MainActivity
import com.example.amunstore.R
import com.example.amunstore.data.model.cart.ItemCart
import com.example.amunstore.databinding.FragmentCartBinding
import com.example.amunstore.ui.auth.AuthActivity
import com.example.amunstore.ui.cart.addresses.AddressesBottomSheetDialogFragment
import com.example.amunstore.ui.cart.coupon.CouponBottomSheetDialogFragment
import com.example.amunstore.ui.intro.IntroActivity
import com.example.amunstore.ui.wallet.activity.CheckoutActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

@AndroidEntryPoint
class CartFragment : Fragment() {

    private val viewModel: CartViewModel by viewModels()

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private var discountValue: Float = 0.0f
    private lateinit var cartAdapter: CartAdapter
    private var totalPrice = 0.0f
    private var totalAmount = 0.0f

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentCartBinding.inflate(inflater, container, false)
        val root: View = binding.root


        cartAdapter = CartAdapter(
            arrayListOf(),
            { viewModel.removeItem(it) },
            { viewModel.increaseItemQuantity(it) },
            { viewModel.decreaseItemQuantity(it) }

        )
        binding.recyclerView.adapter = cartAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getCartItems()
            viewModel.getUserAddresses()
        }

        viewModel.userAddress.observe(viewLifecycleOwner) {
            binding.customerNameTextView.text = it.firstName + " " + it.lastName
            binding.addressTextView.text = it.address1

            if (binding.addressTextView.text.isEmpty())
                binding.changeAddressAppCompactButton.text = "Add address"
            else
                binding.changeAddressAppCompactButton.text = "Change"
        }

        viewModel.data.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                binding.emptyCartLottieView.visibility = View.GONE
                binding.continueShoppingButton.visibility = View.GONE
                binding.containerScrollView.visibility = View.VISIBLE
                binding.continueTextView.visibility = View.VISIBLE
                cartAdapter.changeList(it as MutableList<ItemCart>)

                totalAmount = 0.0f
                totalPrice = 0.0f
                for (item in it) {
                    if (item.price != null && item.item_number != null)
                        totalPrice += item.price.toFloat().times(item.item_number!!)
                }
                setPriceDetails()

            } else {
                binding.emptyCartLottieView.visibility = View.VISIBLE
                binding.continueShoppingButton.visibility = View.VISIBLE
                binding.containerScrollView.visibility = View.GONE
                binding.continueTextView.visibility = View.GONE
            }

        }

        binding.changeAddressAppCompactButton.setOnClickListener {
            val fragment = AddressesBottomSheetDialogFragment {
                binding.customerNameTextView.text = it.firstName + " " + it.lastName
                binding.addressTextView.text = it.address1
            }
            fragment.show(childFragmentManager, "Address")
        }
        binding.applyCouponAppCompactButton.setOnClickListener {
            val fragment = CouponBottomSheetDialogFragment {
                binding.couponTextView.text = it.title
                discountValue = (it.value)?.toFloat() ?: 0.0f
                totalAmount = 0.0f
                setPriceDetails()
            }
            fragment.show(childFragmentManager, "coupon")
        }

        binding.continueShoppingButton.setOnClickListener {
            findNavController().popBackStack()
        }

        viewModel.userName.observe(viewLifecycleOwner) {
            binding.customerNameTextView.text = it
        }

        viewModel.loadUserName()
        binding.continueTextView.setOnClickListener {


            if (viewModel.isUserLoggedIn()) {
                if (binding.addressTextView.text.isNotEmpty()) {
                    val intent = Intent(context, CheckoutActivity::class.java)
                    intent.putExtra("order", viewModel.addUserOrder(discountValue))
                    requireActivity().startActivity(intent)
                } else {
                    Toast.makeText(context, "Please add address", Toast.LENGTH_SHORT).show()
                }
            } else
                startActivity(Intent(context, AuthActivity::class.java))

        }

        if (!viewModel.isUserLoggedIn()) {
            binding.containerAddressConstraintLayout.visibility = View.GONE
        }
        return root
    }

    private fun setPriceDetails() {
        totalAmount = totalPrice - discountValue.absoluteValue
        binding.totalPriceTextView.text = "$totalPrice  L.E"
        binding.totalAmountTextView.text = "${totalAmount.roundToInt()}  L.E"
        binding.discountTextView.text = "$discountValue L.E"
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}