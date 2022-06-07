package com.example.amunstore.ui.auth.login

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.amunstore.MainActivity
import com.example.amunstore.databinding.DialogLoginSuccessfulBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SigninSucessfulBottomSheetDialogFragment : BottomSheetDialogFragment() {
    private var _binding: DialogLoginSuccessfulBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogLoginSuccessfulBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.dialogSuccessfulButton.setOnClickListener { startMainActivity() }

        return root
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        startMainActivity()
    }

    private fun startMainActivity() {
        val intent = Intent(context, MainActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }

}