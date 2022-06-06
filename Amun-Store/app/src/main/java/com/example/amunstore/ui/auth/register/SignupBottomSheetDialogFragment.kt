package com.example.amunstore.ui.auth.register

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
import com.example.amunstore.databinding.DialogSignupWithEmailBinding
import com.example.amunstore.ui.auth.AuthViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint


class SignupBottomSheetDialogFragment(val viewModel: AuthViewModel) : BottomSheetDialogFragment() {
    private var _binding: DialogSignupWithEmailBinding? = null
    private val binding get() = _binding!!



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogSignupWithEmailBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.dialogSignupCloseImageView.setOnClickListener { FragmentManager.POP_BACK_STACK_INCLUSIVE } // to close dialog

//        binding.dialogSignupSignupBtn.setOnClickListener { viewModel.getUserByEmail(binding.dialogLoginEmailIdEdt.text.toString()) }



        viewModel.users.observe(viewLifecycleOwner){
            if (it){
                var intent = Intent(context, MainActivity::class.java)
                startActivity(intent)
                activity?.finish();
            }
            else {
                Toast.makeText(context,getString(R.string.login_failed), Toast.LENGTH_LONG).show()
            }
        }










        return root
    }

    private fun showBottomSheetDialogFragment() {
        val bottomSheetFragment = SignupBottomSheetDialogFragment(viewModel)
        bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)
    }
}