package com.example.amunstore.ui.details

import android.graphics.Paint
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.amunstore.R
import com.example.amunstore.data.model.cart.ItemCart
import com.example.amunstore.data.model.details.ProductDetailsResponse
import com.example.amunstore.data.model.product.Images
import com.example.amunstore.databinding.FragmentProductDetailsBinding
import com.google.android.material.appbar.MaterialToolbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ProductDetailsFragment : Fragment() {
    private var productID: Long = 0L
    private val viewModel: ProductDetailsViewModel by viewModels()
    private var _binding: FragmentProductDetailsBinding? = null
    private val binding get() = _binding!!
    private val args: ProductDetailsFragmentArgs by navArgs()
    private var selectedSize: String? = null

    //viewPager components
    private lateinit var productImagesList: List<Images>
    private lateinit var viewPager: ViewPager2
    private lateinit var viewPagerAdapter: DetailsSliderViewPagerAdapter
    private lateinit var dots: Array<TextView?>
    private var onImageSliderChange = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            addDots(position)
            imagNumebr = position
        }
    }
    private var imagNumebr: Int = 0
    private var colorAdapter: ProductDetailsColorAdapter? = null
    private var sizeAdapter: ProductDetailsSizeAdapter? = null
    private lateinit var colorRecyclerView: RecyclerView
    private lateinit var sizeRecyclerView: RecyclerView

    private lateinit var topAppBar: MaterialToolbar
    private lateinit var favButton: MenuItem

    private lateinit var productDetails: ProductDetailsResponse
    private var variantNumber = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentProductDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        viewPager = binding.productImageView
        productID = args.productId
        viewModel.getProductDetails(productID)
        val navController =
            requireActivity().findNavController(R.id.nav_host_fragment_activity_main)

        colorRecyclerView = binding.productProductPhotsRecycler
        sizeRecyclerView = binding.productSizeRecycler

        topAppBar = binding.topAppBar
        favButton = topAppBar.menu.findItem(R.id.favorite)
        topAppBar.setNavigationOnClickListener {
            this.findNavController().popBackStack()
        }

        binding.productImageView.setOnClickListener {
//            showZoomableImage(productDetails.product.image?.src?.get(imagNumebr).toString())
        }
        topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.favorite -> {
                    viewModel.addOrRemoveProductToFavourite(productDetails.product)
                    true
                }
                R.id.bag -> {
                    navController.navigate(R.id.cartFragment)
                    true
                }
                else -> false
            }
        }

        viewModel.productDetails.observe(viewLifecycleOwner) {
            productDetails = it
            initFragmentAdapters(it)
            setDataToScreen(it, 0)
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        }

        binding.productSaveButton.setOnClickListener {
            addProductToCart()
            Toast.makeText(context, "Added to cart", Toast.LENGTH_SHORT).show()
        }

        viewModel.isProductInFavourite(productID = productID)
        viewModel.isInFavourite.observe(viewLifecycleOwner) {
            if (it) {
                favButton.setIcon(R.drawable.red_heart)
            } else {
                favButton.setIcon(R.drawable.heart)
            }
        }
        binding.productBuyButton.setOnClickListener {
            addProductToCart()
            navController.navigate(R.id.cartFragment)
        }
        return root
    }

    private fun addProductToCart() {
        productDetails.product.id?.let { it ->
            ItemCart(
                it,
                productDetails.product.title,
                productDetails.product.variants[variantNumber].price,
                productDetails.product.image?.src,
                1,
                size = selectedSize ?: productDetails.product.options[0].values[variantNumber],
                maxItem = productDetails.product.variants[variantNumber].inventoryQuantity?.minus(1),
                variant_id = productDetails.product.variants[variantNumber].id!!
            )
        }?.let { it2 -> viewLifecycleOwner.lifecycleScope.launch { viewModel.addToCart(it2) } }
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

    private fun initFragmentAdapters(it: ProductDetailsResponse?) {
        var linear = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        colorRecyclerView.layoutManager = linear

        linear = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        sizeRecyclerView.layoutManager = linear

        colorAdapter = ProductDetailsColorAdapter(it)
        binding.productProductPhotsRecycler.adapter = colorAdapter

        sizeAdapter = ProductDetailsSizeAdapter(it!!.product.options[0].values) {
            selectedSize = it
        }
        binding.productSizeRecycler.adapter = sizeAdapter
        //inflating view pager and it's dot from the respond of the api
        productImagesList = productDetails.product.images
        dots = arrayOfNulls(productImagesList.size)
        viewPagerAdapter = DetailsSliderViewPagerAdapter(productImagesList)
        viewPager.apply {
            adapter = viewPagerAdapter
            registerOnPageChangeCallback(onImageSliderChange)
        }
        sizeAdapter?.checkedItemPosition?.observe(viewLifecycleOwner) {
            setDataToScreen(productDetails, it)
            variantNumber = it
        }
    }

    private fun setDataToScreen(it: ProductDetailsResponse?, variantNumber: Int) {
        if (it != null) {
            binding.productColorTxt.text = it.product.variants[variantNumber].option2
            binding.productTitleText.text = it.product.title
            binding.productVendorText.text = it.product.vendor

            binding.productPriceText.text =
                "${it.product.variants[variantNumber].price} L.E"

            binding.productProductDetailsTxt.text = "\u2022 ${it.product.bodyHtml}"

            //discount
            if (it.product.variants[variantNumber].compareAtPrice.isNullOrEmpty()) {
                binding.productOldPriceTxt.visibility = View.INVISIBLE
                binding.productPricePercentTxt.visibility = View.INVISIBLE
            } else {
                binding.productOldPriceTxt.text =
                    it.product.variants[variantNumber].compareAtPrice + "L.E"
                binding.productOldPriceTxt.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                binding.productOldPriceTxt.paintFlags = binding.productOldPriceTxt.paintFlags
                val percent =
                    it.product.variants[variantNumber].price!!.toDouble() / it.product.variants[variantNumber].compareAtPrice?.toDouble()!! * 100
                binding.productPricePercentTxt.text = " ${percent.toInt()}% OFF"
            }

            binding.productNumberOfLeftTxt.text =
                "ONLY ${it.product.variants[variantNumber].inventoryQuantity} LEFT"

            // to make buy button invisible if the product is inactive
            if (!it.product.status.equals("active")) {
                binding.productBuyButton.isEnabled = false
                binding.productBuyButton.text = getString(R.string.not_available)
            } else {
                binding.productBuyButton.isEnabled = true
                binding.productBuyButton.text = getString(R.string.buy_now)
            }
            if (it.product.variants[variantNumber].inventoryQuantity == 0) {
                binding.productBuyButton.isEnabled = false
                binding.productSaveButton.isEnabled = false
                binding.productBuyButton.text = getString(R.string.not_available)
            } else {
                binding.productBuyButton.isEnabled = true
                binding.productSaveButton.isEnabled = true
                binding.productBuyButton.text = getString(R.string.buy_now)
            }
        }
    }

}