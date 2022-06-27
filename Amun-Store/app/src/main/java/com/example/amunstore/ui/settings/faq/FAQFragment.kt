package com.example.shopy.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.amunstore.data.model.settings.listFQA
import com.example.amunstore.databinding.FragmentFAQBinding
import com.example.myapplication.FAQAdapter


class FAQFragment : Fragment() {
    lateinit var binding: FragmentFAQBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentFAQBinding.inflate(inflater, container, false)

        binding.faqRecView.adapter = FAQAdapter(listFQA)

        return binding.root
    }



}