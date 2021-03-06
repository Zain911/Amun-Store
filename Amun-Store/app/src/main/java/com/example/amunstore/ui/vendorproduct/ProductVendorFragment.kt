package com.example.amunstore.ui.vendorproduct

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.amunstore.R
import com.example.amunstore.databinding.FragmentProductVendorBinding
import com.example.amunstore.data.model.product.Product
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
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentProductVendorBinding.inflate(inflater, container, false)
        val root: View = binding.root

        productAdapter = VendorProductAdapter(arrayListOf(), {
            viewModel.addItemToFavourite(it)
        }, {
            viewModel.removeItemFromFavourite(it)
        },
            {
                val action =
                    it.id?.let { it1 ->
                        ProductVendorFragmentDirections.actionProductVendorToProductDetailsFragment(
                            it1)
                    }
                view?.let { it1 ->
                    action?.let { it2 ->
                        Navigation.findNavController(it1).navigate(it2)
                    }
                }
                // nav?.navigate(action)
            }
        )
        binding.nameVendorTextView.text = args.smartCollection.title

        context?.let {
            Glide.with(binding.imageVendorAppImageView.context)
                .load(args.smartCollection.image?.src)

                .placeholder(R.drawable.brand_mock)
                .into(binding.imageVendorAppImageView)
        }
        binding.productsVendorRecycleView.adapter = productAdapter
        viewModel.products.observe(viewLifecycleOwner) {
            productAdapter.changeList(it as MutableList<Product>)
        }
        lifecycle.coroutineScope.launch {
            viewModel.getProducts(args.smartCollection.id.toString())
        }
        return root

    }


}

