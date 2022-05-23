package com.example. amunstore.ui.home

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.example.amunstore.R

import com.example.amunstore.databinding.FragmentHomeBinding
import com.example.amunstore.model.getImage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    private lateinit var viewPager: ViewPager2
    private lateinit var viewPagerAdapter: SliderViewPagerAdapter
    private var dots: Array<TextView?> = arrayOfNulls<TextView>(getImage().size)
    private var onImageSliderChange =object :ViewPager2.OnPageChangeCallback(){
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
        val homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewPager = binding.adsViewPager
        viewPagerAdapter = SliderViewPagerAdapter(getImage())

        viewPager.apply {
            adapter = viewPagerAdapter
            registerOnPageChangeCallback(onImageSliderChange)
        }
        lifecycleScope.launch {
            while (true) for (i in 0..getImage().size) {
                delay(1500)
                if (i == 0) viewPager.setCurrentItem(i, false) else {
                    viewPager.setCurrentItem(i, true)

                }
            }
        }


        val root: View = binding.root


        return root
    }

    private fun addDots(currentImage: Int) {
        binding.linearLayoutDots.removeAllViews()
        for (i in getImage().indices) {
            dots[i] = TextView(context)
            dots[i]?.text = Html.fromHtml("&#8226", Html.FROM_HTML_MODE_LEGACY)
            dots[i]?.textSize  =32f
            dots[i]?.setTextColor(ContextCompat.getColor(requireContext(),R.color.inactive_dots))
            binding.linearLayoutDots.addView(dots[i])
        }
        if (dots.isNotEmpty())
            dots[currentImage]?.setTextColor(ContextCompat.getColor(requireContext(),
              R.color.primary_color))


    }
    override fun onDestroyView() {
        super.onDestroyView()
        viewPager.unregisterOnPageChangeCallback(onImageSliderChange)
        _binding = null
    }
}