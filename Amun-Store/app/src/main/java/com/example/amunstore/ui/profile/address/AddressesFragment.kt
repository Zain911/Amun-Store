package com.example.amunstore.ui.profile.address

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.amunstore.R
import com.example.amunstore.databinding.FragmentAddressesBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddressesFragment : Fragment() {

    private val viewModel: AddressesViewModel by viewModels()

    private var _binding: FragmentAddressesBinding? = null
    private val binding get() = _binding!!

    private lateinit var addressAdapter: AddressesAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAddressesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        lifecycleScope.launch {
            viewModel.getUserAddresses()
        }

        addressAdapter = AddressesAdapter(arrayListOf(), {
            lifecycleScope.launch {
                viewModel.setAddressAsDefault(it)
                viewModel.getUserAddresses()
            }
        }) {

            val builder =
                AlertDialog.Builder(context, R.style.AlertDialogTheme)
                    .setCancelable(false)
                    .setMessage(getString(R.string.do_you_want_to_delete_this_address))
                    .setPositiveButton(getString(R.string.yes)) { dialog, _ ->
                        lifecycleScope.launch {

                            viewModel.deleteUserAddress(it)
                            viewModel.getUserAddresses()
                        }
                        dialog.dismiss()
                    }.setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                        dialog.dismiss()
                    }

            val alert = builder.create()
            alert.show()

        }

        binding.addressesRecyclerView.adapter = addressAdapter

        viewModel.defaultAddress.observe(viewLifecycleOwner) {

            binding.addressContainerConstraintLayout.visibility = View.VISIBLE

            binding.defaultAddressTypeTextView.text = it.company ?: "Home"
            binding.defaultAddressNameTextView.text = it.firstName + " " + it.lastName
            binding.defaultAddressAddress1TextView.text =
                it.address1 + ", " + it.city + ", " + it.country

            binding.removeDefaultAddressLinearLayout.setOnClickListener { _ ->
                lifecycleScope.launch {
                    viewModel.deleteUserAddress(it)
                    viewModel.getUserAddresses()
                }
            }
        }

        binding.addNewAddressLinearLayout.setOnClickListener {
            findNavController().navigate(R.id.addressFragment)
        }

        viewModel.addressList.observe(viewLifecycleOwner) {
            addressAdapter.changeList(it as MutableList)
        }

        binding.toolbarTitle.setOnClickListener { findNavController().popBackStack() }
        return root
    }

}