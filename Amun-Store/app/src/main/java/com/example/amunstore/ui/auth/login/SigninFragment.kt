package com.example.amunstore.ui.auth.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewTreeLifecycleOwner
import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.amunstore.MainActivity
import com.example.amunstore.R
import com.example.amunstore.databinding.FragmentLoginBinding
import com.example.amunstore.ui.auth.AuthViewModel
import com.example.amunstore.ui.favourites.FavouritesViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.internal.managers.FragmentComponentManager

@AndroidEntryPoint
class SigninFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.loginLoginWithEmailBtn.setOnClickListener { showBottomSheetDialogFragment() }

        binding.loginSignupTxt.setOnClickListener {  findNavController().navigate(
            ActionOnlyNavDirections(R.id.action_loginFragment_to_registerFragment)
        ) }


        return root
    }

//    private fun tryAgain(message:String) {
//        Toast.makeText(context,message , Toast.LENGTH_LONG).show()
//        loginBtn.visibility = View.VISIBLE
//    }


    private fun showBottomSheetDialogFragment() {
        val bottomSheetFragment = LoginBottomSheetDialogFragment(viewModel)
        bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)
    }

}