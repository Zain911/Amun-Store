package com.example.amunstore.ui.auth.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.fragment.findNavController
import com.example.amunstore.R
import com.example.amunstore.databinding.FragmentLoginBinding
import com.example.amunstore.ui.auth.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SigninFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.loginLoginWithEmailBtn.setOnClickListener { showBottomSheetDialogFragment() }

        binding.loginSignupTxt.setOnClickListener {  findNavController().navigate(
            ActionOnlyNavDirections(R.id.action_loginFragment_to_registerFragment)
        ) }

        return root
    }

    private fun showBottomSheetDialogFragment() {
        val bottomSheetFragment = LoginBottomSheetDialogFragment(viewModel)
        bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)
    }

}