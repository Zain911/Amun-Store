package com.example.amunstore.ui.auth.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.amunstore.MainActivity
import com.example.amunstore.databinding.OrderDialogBottomFragmentBinding
import com.example.amunstore.ui.auth.AuthViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderCompletedFragment : BottomSheetDialogFragment() {
    private var _binding: OrderDialogBottomFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AuthViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = OrderDialogBottomFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.dialogLoginSuccessfullyImage.setOnClickListener { done() } // to close dialog
        binding.dialogOrderSuccessfulButton.setOnClickListener {
            done()

        }

        return root
    }

    override fun onDetach() {
        super.onDetach()
        done()
    }

    private fun done() {
        val intent = Intent(requireActivity(), MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
    }

}