package com.example.amunstore.ui.cart.addresses

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.amunstore.R
import com.example.amunstore.databinding.FragmentBottomSheetAddressesBinding
import com.example.amunstore.data.model.address.Address
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddressesBottomSheetDialogFragment(val itemClick: (Address) -> Unit) :
    BottomSheetDialogFragment() {

    private val viewModel: AddressesViewModel by viewModels()

    private var _binding: FragmentBottomSheetAddressesBinding? = null
    private val binding get() = _binding!!

    private lateinit var addressAdapter: AddressAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBottomSheetAddressesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        addressAdapter = AddressAdapter(mutableListOf()) {
            itemClick(it)
        }

        binding.addressesRecyclerView.adapter = addressAdapter

        binding.cancelImageView.setOnClickListener {
            dismiss()
        }

        viewModel.addressesList.observe(viewLifecycleOwner) {
            addressAdapter.setList(it as MutableList<Address>)
        }

        binding.continueAppCompatButton.setOnClickListener {
            dismiss()
        }

        lifecycle.coroutineScope.launch {
            viewModel.getUserAddresses()
        }

        binding.addNewAddressLinearLayout.setOnClickListener {
            findNavController().navigate(R.id.addressFragment)
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}