package com.example.amunstore.ui.auth.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.amunstore.databinding.DialogLoginWithEmailBinding
import com.example.amunstore.databinding.DialogSignupSuccessfulBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SignupSuccesfulBottomSheetDialogFragment : BottomSheetDialogFragment() {
    private var _binding: DialogSignupSuccessfulBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogSignupSuccessfulBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.dialogLoginSuccessfullyImage.setOnClickListener { this@SignupSuccesfulBottomSheetDialogFragment.dismiss() }
        binding.dialogSuccessfulButton.setOnClickListener { this@SignupSuccesfulBottomSheetDialogFragment.dismiss() }

        return root
    }


}