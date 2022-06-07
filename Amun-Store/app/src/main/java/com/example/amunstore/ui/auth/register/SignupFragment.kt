package com.example.amunstore.ui.auth.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.fragment.findNavController
import com.example.amunstore.R
import com.example.amunstore.databinding.FragmentSignupBinding
import com.example.amunstore.ui.auth.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupFragment : Fragment() {
    private lateinit var _binding: FragmentSignupBinding
    private val binding get() = _binding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignupBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.signupSignupWithEmailBtn.setOnClickListener { showBottomSheetDialogFragment() }

        binding.signinLogin.setOnClickListener {
            findNavController().navigate(
                ActionOnlyNavDirections(R.id.action_registerFragment_to_loginFragment)
            )
        }

        return root
    }

    private fun showBottomSheetDialogFragment() {
        val bottomSheetFragment = SignupBottomSheetDialogFragment(viewModel)
        bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)
    }
}