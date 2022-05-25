package com.example.amunstore.ui.vendorproduct

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.amunstore.R
import com.example.amunstore.databinding.FragmentProductVendorBinding
import com.example.amunstore.model.product.Products
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductVendorFragment : Fragment(R.layout.fragment_product_vendor) {

    private val args: ProductVendorFragmentArgs by navArgs()
    private var _binding: FragmentProductVendorBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProductVendorViewModel by viewModels()
    private lateinit var productAdapter: VendorProductAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductVendorBinding.inflate(inflater, container, false)
        val root: View = binding.root

        productAdapter = VendorProductAdapter(arrayListOf())
        binding.nameVendorTextView.text = args.smartCollection.title

        context?.let {
            Glide.with(binding.imageVendorAppImageView.context)
             .load(args.smartCollection.image?.src)

                .placeholder(R.drawable.brand_mock)
                .into(binding.imageVendorAppImageView)
        }
        binding.productsVendorRecycleView.adapter = productAdapter
        viewModel.products.observe(viewLifecycleOwner) {
            productAdapter.changeList(it as MutableList<Products>)
        }
        lifecycle.coroutineScope.launch {
            viewModel.getProducts(args.smartCollection.id.toString())
        }
        return root

    }


}