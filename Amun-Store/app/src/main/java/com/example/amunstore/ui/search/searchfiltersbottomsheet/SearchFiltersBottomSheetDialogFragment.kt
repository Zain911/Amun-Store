package com.example.amunstore.ui.search.searchfiltersbottomsheet

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import com.example.amunstore.R
import com.example.amunstore.data.model.address.Address
import com.example.amunstore.databinding.FragmentBottomSheetAddressesBinding
import com.example.amunstore.databinding.FragmentBottomSheetSearchFiltersBinding
import com.example.amunstore.ui.cart.addresses.AddressAdapter

import com.example.amunstore.ui.cart.addresses.AddressesViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch

class SearchFiltersBottomSheetDialogFragment(val filter: String, val itemClick: (String) -> Unit) :
    BottomSheetDialogFragment() {

    private var _binding: FragmentBottomSheetSearchFiltersBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBottomSheetSearchFiltersBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.lowToHighLinearLayout.setOnClickListener {
            itemClick("price: Low to High")
            dismiss()
        }

        binding.highToLowLinearLayout.setOnClickListener {
            itemClick("price: High to Low")
            dismiss()
        }

        binding.alphabeticalLinearLayout.setOnClickListener {
            itemClick("Alphabetically")
            dismiss()
        }

        binding.allLinearLayout.setOnClickListener {
            itemClick("none")
            dismiss()
        }
        binding.cancelImageView.setOnClickListener {
            dismiss()
        }

        when (filter) {
            "price: Low to High" -> {
                binding.lowToHighImageView.setImageResource(R.drawable.checked_circle)
            }

            "price: High to Low" -> {
                binding.highToLowImageView.setImageResource(R.drawable.checked_circle)
            }

            "Alphabetically" -> {
                binding.alphabeticalImageView.setImageResource(R.drawable.checked_circle)
            }

            "none" -> {
                binding.allImageView.setImageResource(R.drawable.checked_circle)
            }
        }

        return root
    }

}