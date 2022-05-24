package com.example.amunstore.ui.vendorproduct

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.amunstore.R
import com.example.amunstore.databinding.FragmentProductVendorBinding

class ProductVendor : Fragment(R.layout.fragment_product_vendor) {

    private var _binding: FragmentProductVendorBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ProductVendorViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductVendorBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root

    }


}