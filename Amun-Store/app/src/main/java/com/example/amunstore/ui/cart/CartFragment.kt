package com.example.amunstore.ui.cart

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.amunstore.data.model.cart.ItemCart
import com.example.amunstore.data.model.order.AddOrderRequestModel
import com.example.amunstore.data.model.order.Order
import com.example.amunstore.databinding.FragmentCartBinding
import com.example.amunstore.ui.cart.addresses.AddressesBottomSheetDialogFragment
import com.example.amunstore.ui.cart.coupon.CouponBottomSheetDialogFragment
import com.example.example.LineItems
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@AndroidEntryPoint
class CartFragment : Fragment() {

    private val viewModel: CartViewModel by viewModels()

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private var discountValue: Float = 0.0f
    private lateinit var cartAdapter: CartAdapter

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
            binding.addressTextView.text = it
        }

        viewModel.data.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                binding.emptyCartLottieView.visibility = View.GONE
                binding.continueShoppingButton.visibility = View.GONE
                binding.containerScrollView.visibility = View.VISIBLE
                cartAdapter.changeList(it as MutableList<ItemCart>)

                var totalAmount = 0.0f
                var totalPrice = 0.0f
                for (item in it) {
                    if (item.price != null && item.item_number != null)
                        totalPrice += item.price.toFloat().times(item.item_number!!)
                }
                totalAmount = totalPrice - discountValue.absoluteValue
                binding.totalPriceTextView.text = "$totalPrice  L.E"
                binding.totalAmountTextView.text = "$totalAmount  L.E"
                binding.discountTextView.text = "$discountValue L.E"
            } else {
                binding.emptyCartLottieView.visibility = View.VISIBLE
                binding.continueShoppingButton.visibility = View.VISIBLE
                binding.containerScrollView.visibility = View.GONE
            }

        }

        binding.changeAddressAppCompactButton.setOnClickListener {
            val fragment = AddressesBottomSheetDialogFragment {
                binding.addressTextView.text = it.address1
            }
            fragment.show(childFragmentManager, "Address")
        }
        binding.applyCouponAppCompactButton.setOnClickListener {
            val fragment = CouponBottomSheetDialogFragment {
                binding.couponTextView.text = it.title
                discountValue = (it.value)?.toFloat() ?: 0.0f
                binding.discountTextView.text = "$discountValue L.E"
            }
            fragment.show(childFragmentManager, "Address")
        }

        binding.continueShoppingButton.setOnClickListener {
            findNavController().navigateUp()
        }

        viewModel.userName.observe(viewLifecycleOwner) {
            binding.customerNameTextView.text = it
        }

        viewModel.loadUserName()
        binding.continueTextView.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch { viewModel.addUserOrder(discountValue)//50 is total price
             }
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}