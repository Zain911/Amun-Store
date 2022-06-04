package com.example.amunstore.ui.details

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.amunstore.R
import com.example.amunstore.data.model.details.ProductDetailsResponse
import com.example.amunstore.data.model.product.Images
import com.example.amunstore.databinding.FragmentProductDetailsBinding
import com.google.android.material.appbar.MaterialToolbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ProductDetailsFragment() : Fragment() {

    private val viewModel: ProductDetailsViewModel by viewModels()
    private var _binding: FragmentProductDetailsBinding? = null
    private val binding get() = _binding!!
    private val args: ProductDetailsFragmentArgs by navArgs()

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

    var colorAdapter: ProductDetailsColorAdapter? = null
    var sizeAdapter: ProductDetailsSizeAdapter? = null
    lateinit var colorRecyclerView: RecyclerView
    lateinit var sizeRecyclerView: RecyclerView

    lateinit var topAppBar: MaterialToolbar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentProductDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        viewPager = binding.productImageView
        viewModel.getProductDetails(args.productId)

        colorRecyclerView=binding.productProductPhotsRecycler
        sizeRecyclerView = binding.productSizeRecycler

        topAppBar   =binding.topAppBar

        topAppBar.setNavigationOnClickListener {
         this.findNavController().popBackStack()
        }

        topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.favorite -> {
                    // Handle edit text press
                    true
                }
                R.id.share -> {
                    // Handle favorite icon press
                    true
                }
                R.id.bag -> {
                    // Handle more item (inside overflow menu) press
                    true
                }
                else -> false
            }
        }

        viewModel.productDetails.observe(viewLifecycleOwner) {
            initFragmentAdapters(it)
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

    fun initFragmentAdapters(it : ProductDetailsResponse?){
        var linear = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        colorRecyclerView.layoutManager = linear

        linear = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        sizeRecyclerView.layoutManager = linear

        colorAdapter = ProductDetailsColorAdapter(it)
            binding.productProductPhotsRecycler.adapter = colorAdapter

        sizeAdapter = ProductDetailsSizeAdapter(it!!.product.options[0].values)
            binding.productSizeRecycler.adapter = sizeAdapter

    }





    fun setDataToScreen(it : ProductDetailsResponse?){
        if (it != null) {
            binding.productColorTxt.text=it.product.options[1].values[0]
            binding.productTitleText.text = it.product.title
            binding.productVendorText.text = it.product.vendor
            if (sizeAdapter?.checkedItemPosition !=-1)
            binding.productPriceText.text = "${it.product.variants[sizeAdapter!!.checkedItemPosition].price} ${viewModel.getCurrencyInfoForDefaultLocale()}"
            else
                binding.productPriceText.text =   "${it.product.variants[0].price} ${viewModel.getCurrencyInfoForDefaultLocale()}"

            binding.productProductDetailsTxt.text = "\u2022 ${it.product.bodyHtml}"

            if (it.product.variants[0].compareAtPrice.isNullOrEmpty()) {
                binding.productOldPriceTxt.visibility = View.INVISIBLE
                binding.productPricePercentTxt.visibility = View.INVISIBLE
            }
            else {
                binding.productOldPriceTxt.text = it.product.variants[0].compareAtPrice
                binding.productOldPriceTxt.setPaintFlags( binding.productOldPriceTxt.getPaintFlags()  );
                val percent :Int = it.product.variants[0].compareAtPrice!!.toInt() / it.product.variants[0].price?.toInt()!! *100
                binding.productPricePercentTxt.text = " ${percent}% OFF"
            }
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



        }
    }

}



