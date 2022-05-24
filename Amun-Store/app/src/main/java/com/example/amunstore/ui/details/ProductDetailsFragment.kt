package com.example.amunstore.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.amunstore.R

import com.example.amunstore.databinding.FragmentProductDetailsBinding
import com.example.amunstore.ui.categories.ViewPagerCategoriesAdapter
import com.example.amunstore.ui.categories.singlecategory.SingleCategoryFragment
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ProductDetailsFragment : Fragment() {


    private val viewModel: ProductDetailsViewModel by viewModels()
    private var _binding: FragmentProductDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModel.productDetails.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.productTitleText.text=it.product.title
                binding.productVendorText.text=it.product.vendor
                binding.productPriceText.text=it.product.variants[0].price
                binding.productBodyHtmlText.text=it.product.body_html
                binding.productStatusText.text=it.product.status   // need some equipments
//                binding.productImageView.setImageResource()   //glide
                Glide.with(requireContext())
                    .load(it.product.image.src)
                    .into(binding.productImageView)

                }




        }


            CoroutineScope(Dispatchers.IO).launch {
            viewModel.getProductDetails(
                savedInstanceState?.getString(getString(R.string.product_id)  ).toString()
            )
        }

        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}



