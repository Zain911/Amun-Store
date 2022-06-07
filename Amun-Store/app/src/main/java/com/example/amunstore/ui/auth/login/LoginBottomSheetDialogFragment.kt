package com.example.amunstore.ui.auth.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import com.example.amunstore.MainActivity
import com.example.amunstore.R
import com.example.amunstore.databinding.DialogLoginWithEmailBinding
import com.example.amunstore.ui.auth.AuthViewModel
import com.example.amunstore.ui.auth.register.SignupBottomSheetDialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint


class LoginBottomSheetDialogFragment(val viewModel: AuthViewModel) : BottomSheetDialogFragment() {
    private var _binding: DialogLoginWithEmailBinding? = null
    private val binding get() = _binding!!



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogLoginWithEmailBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.dialogLoginCloseImageView.setOnClickListener { FragmentManager.POP_BACK_STACK_INCLUSIVE } // to close dialog

        binding.dialogLoginLoginBtn.setOnClickListener { viewModel.getUserByEmail(binding.dialogLoginEmailIdEdt.text.toString(),binding.dialogLoginPasswordEdt2.text.toString() ) }

        //binding.dialogLoginForgotPasswordTxt.setOnClickListener {  }

        viewModel.users.observe(viewLifecycleOwner){
            if (it){
                showBottomSheetDialogFragment()
            }
            else {
                Toast.makeText(context,getString(R.string.login_failed), Toast.LENGTH_LONG).show()
            }
        }

        binding.dialogLoginForgotPasswordTxt.setOnClickListener {  }









        return root
    }

    private fun showBottomSheetDialogFragment() {
        val bottomSheetFragment = LoginSucessfulBottomSheetDialogFragment()
        bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)
    }



}