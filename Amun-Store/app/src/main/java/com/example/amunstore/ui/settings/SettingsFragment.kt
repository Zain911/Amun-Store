package com.example.amunstore.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.amunstore.R
import com.example.amunstore.databinding.FragmentSettingsBinding


class SettingsFragment : Fragment() {
    lateinit var binding: FragmentSettingsBinding
    lateinit var settingsViewModel: SettingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentSettingsBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cvDevelopedBy.setOnClickListener {
        }
        binding.cvAddress.setOnClickListener {

            findNavController().navigate(R.id.addressesFragment)

        }
        binding.cvProfile.setOnClickListener {

        }

        binding.cvAbout.setOnClickListener {
        }

        binding.cvFAQ.setOnClickListener {
            findNavController().navigate(R.id.FAQFragment)
        }

        binding.signout.setOnClickListener {


        }
    }


}