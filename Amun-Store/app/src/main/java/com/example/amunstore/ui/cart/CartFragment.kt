package com.example.amunstore.ui.cart

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.amunstore.R
import com.example.amunstore.data.model.cart.ItemCart
import com.example.amunstore.databinding.FragmentCartBinding
import com.example.amunstore.ui.cart.addresses.AddressesBottomSheetDialogFragment
import com.example.amunstore.ui.cart.coupon.CouponBottomSheetDialogFragment
import com.example.amunstore.ui.wallet.activity.CheckoutActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@AndroidEntryPoint
class CartFragment : Fragment() {

    private val orderViewModel: CartViewModel by viewModels()

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private var discountValue: Float = 0.0f
    private lateinit var cartAdapter: CartAdapter

    var totalAmount = 0.0f
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
            { orderViewModel.removeItem(it) },
            { orderViewModel.increaseItemQuantity(it) },
            { orderViewModel.decreaseItemQuantity(it) }
        )
        binding.recyclerView.adapter = cartAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            orderViewModel.getCartItems()
        }

        orderViewModel.data.observe(viewLifecycleOwner) {
            cartAdapter.changeList(it as MutableList<ItemCart>)
            var totalPrice = 0.0f
            for (item in it) {
                if (item.price != null && item.item_number != null)
                    totalPrice += item.price.toFloat().times(item.item_number!!)
            }
            totalAmount = totalPrice - discountValue.absoluteValue
            binding.totalPriceTextView.text = "$totalPrice  L.E"
            binding.totalAmountTextView.text = "$totalAmount  L.E"
            binding.discountTextView.text = "$discountValue L.E"
        }

        binding.changeAddressAppCompactButton.setOnClickListener {
            val fragment = AddressesBottomSheetDialogFragment()
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
        binding.continueTextView.setOnClickListener {
            if (totalAmount > 0) {
                val intent = Intent(requireActivity(), CheckoutActivity::class.java)
                    .putExtra("price", totalAmount.toString())
                    .putExtra("address", "")
                    .putExtra("orderNumber", "")
                startActivity(intent)
            } else {
                Toast.makeText(requireContext(), getString(R.string.no_orders), Toast.LENGTH_LONG)
                    .show()
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}