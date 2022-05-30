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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.amunstore.R
import com.example.amunstore.data.model.details.ProductDetailsResponse
import com.example.amunstore.data.model.product.Images
import com.example.amunstore.databinding.FragmentProductDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class ProductDetailsFragment() : Fragment() {

    private val viewModel: ProductDetailsViewModel by viewModels()
    private var _binding: FragmentProductDetailsBinding? = null
    private val binding get() = _binding!!

    //viewPager components
    private lateinit var productImagesList: List<Images>
    private lateinit var viewPager: ViewPager2
    private lateinit var viewPagerAdapter: DetailsSliderViewPagerAdapter
    private lateinit var dots: Array<TextView?>
    private var onImageSliderChange = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            addDots(position)
        }
    }

    var adapter: ProductDetailsAdapter? = null
    lateinit var recyclerView: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        viewPager = binding.productImageView
        viewModel.getProductDetails(savedInstanceState?.getLong(getString(R.string.pro_id))
            ?: 7782820643045)

        recyclerView = binding.productListView

        viewModel.productDetails.observe(viewLifecycleOwner) {
            setDataToScreen(it)
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        }

        return root
    }

    private fun addDots(currentImage: Int) {
        binding.productLinearLayoutDots.removeAllViews()
        for (i in productImagesList.indices) {
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
    }

    fun setDataToScreen(it : ProductDetailsResponse?){
        if (it != null) {
            binding.productTitleText.text = it.product.title
            binding.productVendorText.text = it.product.vendor
            binding.productPriceText.text =
                "${it.product.variants[0].price} ${viewModel.getCurrencyInfoForDefaultLocale()}"
            binding.productBodyHtmlText.text = it.product.bodyHtml
            binding.productDateTxt.text = viewModel.modifyDateLayout(it.product.createdAt.toString())
            // to make buy button invisible if the priduct is inactive
            if (!it.product.status.equals("active")) {
                binding.productBuyButton.visibility = View.INVISIBLE
                binding.productBuyButton.text = getString(R.string.not_available)
            }

            //inflating view pager and it's dot from the respond of the api
            productImagesList = it.product.images
            dots = arrayOfNulls<TextView>(productImagesList.size)
            viewPagerAdapter = DetailsSliderViewPagerAdapter(productImagesList)
            viewPager.apply {
                adapter = viewPagerAdapter
                registerOnPageChangeCallback(onImageSliderChange)
            }

            val linear = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            recyclerView.layoutManager = linear
            adapter = ProductDetailsAdapter(it)
            binding.productListView.adapter = adapter

        }
    }

}



