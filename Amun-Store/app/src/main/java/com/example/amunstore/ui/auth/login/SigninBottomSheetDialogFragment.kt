package com.example.amunstore.ui.auth.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.amunstore.R
import com.example.amunstore.databinding.DialogLoginWithEmailBinding
import com.example.amunstore.ui.auth.AuthViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SigninBottomSheetDialogFragment : BottomSheetDialogFragment() {
    private var _binding: DialogLoginWithEmailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AuthViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogLoginWithEmailBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.dialogLoginCloseImageView.setOnClickListener { dismiss() } // to close dialog
        binding.dialogLoginLoginBtn.setOnClickListener {
            viewModel.getUserByEmail(
                binding.dialogLoginEmailIdEdt.text.toString(),
                binding.dialogLoginPasswordEdt2.text.toString()
            )
        }

        viewModel.users.observe(viewLifecycleOwner) {
            if (it) {
                showBottomSheetDialogFragment()
            }
            else{ Toast.makeText(
                context,
                getString(R.string.login_failed),
                Toast.LENGTH_LONG
            ).show() }
        }

        binding.dialogLoginForgotPasswordTxt.setOnClickListener {
            Toast.makeText(
                context,
                getString(R.string.not_available),
                Toast.LENGTH_LONG
            ).show()
        }


        binding.dialogLoginEmailIdEdt.setText("test1@mail.com")
        binding.dialogLoginPasswordEdt2.setText("123456789")

        return root
    }

    private fun showBottomSheetDialogFragment() {
        val bottomSheetFragment = SigninSucessfulBottomSheetDialogFragment()
        bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)
    }
}