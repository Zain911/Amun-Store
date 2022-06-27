package com.example.amunstore.ui.settings.developed

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.amunstore.data.model.settings.develperList
import com.example.amunstore.databinding.FragmentDevelopedByBinding


class DevelopedByFragment : Fragment() {
    lateinit var binding: FragmentDevelopedByBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentDevelopedByBinding.inflate(inflater, container, false)

        binding.devByRecView.adapter = DevByAdapter(develperList)
        binding.toolbarTitle.setOnClickListener { findNavController().popBackStack() }
        return binding.root
    }


}