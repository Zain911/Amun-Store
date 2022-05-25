package com.example.amunstore.ui.details

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.amunstore.R

import com.example.amunstore.databinding.FragmentProductDetailsBinding
import com.example.amunstore.model.getImage
import com.example.amunstore.ui.categories.ViewPagerCategoriesAdapter
import com.example.amunstore.ui.categories.singlecategory.SingleCategoryFragment
import com.example.amunstore.ui.home.SliderViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductDetailsFragment(val productID:Long) : Fragment() {

    private val viewModel: ProductDetailsViewModel by viewModels()
    private var _binding: FragmentProductDetailsBinding? = null
    private val binding get() = _binding!!

    //viewPager components
    private lateinit var job: Job
    private lateinit var viewPager: ViewPager2
    private lateinit var viewPagerAdapter: SliderViewPagerAdapter
    private lateinit var dots: Array<TextView?>
    private var onImageSliderChange = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            addDots(position)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModel.productDetails.observe(viewLifecycleOwner) {

            if (it != null) {
                binding.productTitleText.text = it?.products?.title
                binding.productVendorText.text = it?.products?.vendor
                binding.productPriceText.text= it.products.variants[0].price
                binding.productBodyHtmlText.text = it?.products?.bodyHtml
                binding.productStatusText.text = it?.products?.status   // need some equipments
            }
        }

            viewModel.errorMessage.observe(viewLifecycleOwner) {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()

            }

            viewModel.getProductDetails(productID )

        return root
    }

    private fun addDots(currentImage: Int) {
        binding.productLinearLayoutDots.removeAllViews()
        for (i in getImage().indices) {
            dots[i] = TextView(context)
            dots[i]?.text = Html.fromHtml("&#8226", Html.FROM_HTML_MODE_LEGACY)
            dots[i]?.textSize = 32f
            dots[i]?.setTextColor(ContextCompat.getColor(requireContext(), R.color.inactive_dots))
            binding.productLinearLayoutDots.addView(dots[i])
        }
        if (dots.isNotEmpty())
            dots[currentImage]?.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.primary_color
                )
            )
    }

    override fun onPause() {
        super.onPause()
        viewPager.unregisterOnPageChangeCallback(onImageSliderChange)

    }


    override fun onDestroyView() {
        super.onDestroyView()
        viewPager.unregisterOnPageChangeCallback(onImageSliderChange)
        _binding = null
        job.cancel()
    }

}



