package com.example.amunstore.ui.cart.coupon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import com.example.amunstore.R
import com.example.amunstore.databinding.FragmentBottomSheetAddressesBinding
import com.example.amunstore.data.model.address.Address
import com.example.amunstore.data.model.discount.getDisCount
import com.example.amunstore.databinding.FragmentBottomSheetCouponBinding
import com.example.example.PriceRules
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CouponBottomSheetDialogFragment : BottomSheetDialogFragment() {

    private val viewModel: CouponViewModel by viewModels()

    private var _binding: FragmentBottomSheetCouponBinding? = null
    private val binding get() = _binding!!

    private lateinit var couponAdapter: CouponAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBottomSheetCouponBinding.inflate(inflater, container, false)
        val root: View = binding.root

      couponAdapter = CouponAdapter(mutableListOf())

        binding.couponsRecyclerView.adapter = couponAdapter



        viewModel.couponList.observe(viewLifecycleOwner) {
            couponAdapter.setList(it as MutableList<PriceRules>)
        }

        lifecycle.coroutineScope.launch {
            viewModel.getAllCoupons()
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}