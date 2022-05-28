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
import com.example.amunstore.R
import com.example.amunstore.databinding.FragmentProductDetailsBinding
import com.example.amunstore.model.product.Images
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class ProductDetailsFragment(val productID: Long) : Fragment() {

    private val viewModel: ProductDetailsViewModel by viewModels()
    private var _binding: FragmentProductDetailsBinding? = null
    private val binding get() = _binding!!

    //viewPager components
    private lateinit var  productImagesList : List<Images>
    private lateinit var job: Job
    private lateinit var viewPager: ViewPager2
    private lateinit var viewPagerAdapter: DetailsSliderViewPagerAdapter
    private lateinit var dots : Array<TextView?>
    private var onImageSliderChange = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            addDots(position)
        }
    }

    var adapter: ProductDetailsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        viewPager = binding.productImageView
        viewModel.getProductDetails(productID)

        viewModel.productDetails.observe(viewLifecycleOwner) {

            if (it != null) {
                binding.productTitleText.text = it.product.title
                binding.productVendorText.text = it.product.vendor
                binding.productPriceText.text = "${it.product.variants[0].price} ${getCurrencyInfoForDefaultLocale()}"
                binding.productBodyHtmlText.text = it.product.bodyHtml
                binding.productDateTxt.text = modifyDateLayout(it.product.createdAt.toString())
                // to make buy button invisible if the priduct is inactive
                if (!it.product.status.equals("active")){
                binding.productBuyButton.visibility = View.INVISIBLE
                }

                //inflating view pager and it's dot from the respond of the api
                productImagesList = it.product.images
                dots =arrayOfNulls<TextView>(productImagesList.size)
                viewPagerAdapter = DetailsSliderViewPagerAdapter(productImagesList)
                viewPager.apply {
                    adapter = viewPagerAdapter
                    registerOnPageChangeCallback(onImageSliderChange)
                }

                adapter = ProductDetailsAdapter(requireContext(), it)
                binding.productListView.adapter = adapter
            }
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
        job.cancel()
    }

    @Throws(ParseException::class)
    private fun modifyDateLayout(inputDate: String): String? {
        val date: Date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").parse(inputDate) as Date
        return SimpleDateFormat("dd.MM.yyyy").format(date)
    }

    fun getCurrencyInfoForDefaultLocale(): String? {
        val defaultLocale = Locale.getDefault()
      //  displayCurrencyInfoForLocale(defaultLocale)
       // System.out.println("Locale: " + locale.displayName)
        val currency = Currency.getInstance(defaultLocale)
        //System.out.println("Currency Code: " + currency.currencyCode)
        //System.out.println("Symbol: " + currency.symbol)
        return currency.symbol
        //System.out.println("Default Fraction Digits: " + currency.defaultFractionDigits)
        //println()
    }
}



