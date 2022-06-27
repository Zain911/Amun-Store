package com.example.amunstore.ui.settings

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.amunstore.R
import com.example.amunstore.databinding.FragmentSettingsBinding
import com.example.amunstore.ui.auth.AuthActivity
import com.example.amunstore.ui.profile.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel


@AndroidEntryPoint
class SettingsFragment : Fragment() {

    lateinit var binding: FragmentSettingsBinding

    private val viewModel: SettingsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSettingsBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cvDevelopedBy.setOnClickListener {
            findNavController().navigate(R.id.developedByFragment)
        }
        binding.cvAddress.setOnClickListener {
            if (viewModel.isUserLoggedIn())
                findNavController().navigate(R.id.addressesFragment)
            else
                startActivity(Intent(context, AuthActivity::class.java))
        }
        binding.cvProfile.setOnClickListener {
            if (viewModel.isUserLoggedIn())
                findNavController().navigate(R.id.navigation_notifications)
            else
                startActivity(Intent(context, AuthActivity::class.java))
        }

        binding.cvAbout.setOnClickListener {
            findNavController().navigate(R.id.aboutFragment)
        }

        binding.cvFAQ.setOnClickListener {
            findNavController().navigate(R.id.FAQFragment)
        }

        binding.signout.setOnClickListener {
            viewModel.clearSharedPreferences()
        }
        binding.backImageView.setOnClickListener { findNavController().popBackStack() }

        if (!viewModel.isUserLoggedIn()) {
            binding.signout.visibility = View.GONE
        }
    }


}